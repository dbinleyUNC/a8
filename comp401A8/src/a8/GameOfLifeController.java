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
