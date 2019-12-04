package a8;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class GameOfLifeController implements ActionListener, GameOfLifeObserver, MouseListener, ChangeListener {
	GameOfLifeView view;
	GameOfLifeModel model;
	Timer t;
	public GameOfLifeController(GameOfLifeModel model, GameOfLifeView view) {
		this.model = model;
		model.addObserver(this);
		this.view = view;
		t = new Timer(model, this);
		view.addActionListener(this);
		view.addMouseListener(this);
		view.addChangeListener(this);
	}
	@Override
	public void update(int x, int y) {
		//-10 is passed when the timer is halted
		if (x == -10) {
			if (view.getPlayButton().getText().equals("Stop")) {
				
				view.getPlayButton().setText("Start");
			}
		}
		//-100 is passed
		if (x == -100) {
			view.render(model.getBoard());
		}
		else if (x<0) {
			view.render(model.getBoard(),model.getPreviousBoard());
		}
		else {
			view.render(model.getBoard(),x,y);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean boardSpots[][];
		JButton button = (JButton) e.getSource();
		char button_char = button.getText().charAt(0);
		switch (button_char) {
		case 'C':
			t.halt();
			model.clearBoard();
			break;
		case 'A':
			if (!t.isRunning()) {
				//model.setBoard(modifyArray(model.getBoard(),true));
				model.advanceSpots();
			}
			break;
		case 'F':
			model.fillRandomBoard();
			break;
		case 'S':
			if (t.isRunning()) {
				t.halt();
				button.setText("Start");
			}
			else {
				t = new Timer(model, this);
				t.start();
				button.setText("Stop");
			}
			break;
		case 'T':
			model.toggleTorusOn();
			if (button.getText().equals("Turn Torus Mode On")) {
				button.setText("Turn Torus Mode Off");
			}
			else {
				button.setText("Turn Torus Mode On");
			}
			break;
		default: break;
		}
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			int width = view.getBoard().getDimensions()/model.getBoard().length;
			int height = width;
			if(y/height <model.getBoard().length && x/width <model.getBoard()[0].length) {
				model.toggleSpot(y/height,x/width);
		}
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if (((JSlider) e.getSource()).getValueIsAdjusting()) {
			return;
		}
		JSlider slider = (JSlider) e.getSource();
		String name = slider.getName();
		if ("Change Size".equals(name)) {
			t.halt();
			view.getBoardSizeMSlider().setValue(slider.getValue());
			int dimension = slider.getValue();
			boolean[][] boardSpots = new boolean[dimension][dimension];
			
			view.getBoard().clear();
			view.getBoard().repaintBlack(view.getGraphics(),boardSpots);
			model.changeDimensions(dimension);
			model.fillRandomBoard();
			
		}
		else if ("Custom Birth Threshold".equals(name)) {
			model.setBirthThreshold(slider.getValue());
			view.getBirthThresholdMSlider().setValue(slider.getValue());
		}
		else if ("Custom Survive Lower Bound".equals(name)) {
			if (slider.getValue() > model.getSurviveThresholdHigh()) {
				slider.setValue(model.getSurviveThresholdHigh());
			}
			model.setSurviveThresholdLow(slider.getValue());
			view.getSurviveThresholdLowMSlider().setValue(slider.getValue());
		}
		else if ("Custom Survive Upper Bound".equals(name)) {
			if (slider.getValue() < model.getSurviveThresholdLow()) {
				slider.setValue(model.getSurviveThresholdLow());
			}
			model.setSurviveThresholdHigh(slider.getValue());
			view.getSurviveThresholdHighMSlider().setValue(slider.getValue());
		}
		else if ("Set Speed".equals(name)) {
			model.setWaitTime(1010 -slider.getValue());
			view.getSpeedMSlider().setValue(1010 -slider.getValue());
			
		}
	}
	/*public boolean[][] modifyArray(boolean array[][], boolean f) {
		boolean finalArray[][] = new boolean[array.length][array[0].length];
		for (int i = 0; i< array.length; i++) {
			for (int j = 0; j< array[i].length; j++) {
				finalArray[i][j] = array[i][j];
			}
		}
		
		for (int i = 0; i< array.length; i++) {
			for (int j = 0; j< array[i].length; j++) {
				int count = 0;
				if (model.getTorusOn()) {
					count = calculateTorusCount(array,i,j);
				}
				else {
					count = calculateNormalCount(array,i,j);
				}
				
				if (!array[i][j] && count ==model.getBirthThreshold()) {
					finalArray[i][j] = true;
				}
				else if (array[i][j] && (count<model.getSurviveThresholdLow() 
						|| count>model.getSurviveThresholdHigh()))  {
					finalArray[i][j] = false;
				}
			
			}
		}
		return finalArray;
	}
	private int calculateTorusCount(boolean[][] array, int i, int j) {
		int ia = i+1;
		int ib = i-1;
		int ja = j+1;
		int jb = j-1;	
		int count = 0;
		
		if (i==0 || j ==0 || j ==array.length-1 || i == array.length-1) {
			if (i== 0) {
				ib = array.length-1;
			}
			if (i == array.length-1) {
				ia = 0;
			}
			if (j == 0) {
				jb = array.length-1;
			}
			if (j==array.length-1) {
				ja = 0;
			}
			if (array[ib][j]) count++;
			if (array[ib][jb]) count++;
			if (array[ib][ja]) count++;
			
			if (array[i][jb]) count++;
			if (array[ia][jb]) count++;
			
			if (array[ia][j]) count++;
			if (array[ia][ja]) count++;
			
			if (array[i][ja]) count++;
			
			return count;
		}
		else {
			return calculateNormalCount(array,i,j);
		}
	}
	
	private int calculateNormalCount(boolean[][] array, int i, int j) {
		int count = 0;
		if (i>0) {
			if (array[i-1][j]) count++;
			if (j>0 && array[i-1][j-1]) count++;
			if (j<array[i].length-1 &&array[i-1][j+1]) count++;
		}
		if (j>0) {
			if (array[i][j-1]) count++;
			if (i < array.length-1 && array[i+1][j-1]) count++;
		}
		if (i<array.length-1) {
			if (array[i+1][j]) count++;
			if (j<array[i].length-1 && array[i+1][j+1]) count++;
		}
		if (j<array[i].length-1 && array[i][j+1]) count++;	
		return count;
		
	}
	*/
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
