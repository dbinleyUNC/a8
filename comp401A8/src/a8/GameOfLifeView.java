package a8;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

import java.util.Iterator;
import java.util.List;


public class GameOfLifeView extends JPanel{
	private JButton reset_button;
	private JButton advance_button;
	private JButton random_button;
	private JButton play_button;
	private JButton torus_button;

	private MessageSlider boardSizeMSlider;
	private MessageSlider speedMSlider;
	private MessageSlider birthThresholdMSlider;
	private MessageSlider surviveThresholdLowMSlider;
	private MessageSlider surviveThresholdHighMSlider;
	
	private JPanel slider_panel;
	
	private JRectangle rect;
	private int height = 200;
	private int width = 200;
	
	

	public GameOfLifeView() {
		
		rect = new JRectangle(600,200);
		setLayout(new BorderLayout());
		add(rect, BorderLayout.CENTER);
		
		slider_panel = new JPanel();
		
		boardSizeMSlider = new MessageSlider("Change Size",10,500, 50);
		slider_panel.add(boardSizeMSlider,BorderLayout.CENTER);
		slider_panel.setLayout(new GridLayout(10,1));
		
		birthThresholdMSlider = new MessageSlider("Custom Birth Threshold",0, 8,3);
		birthThresholdMSlider.setPreferredSize(new Dimension(200, 20));
		slider_panel.add(birthThresholdMSlider);
		
		surviveThresholdLowMSlider = new MessageSlider("Custom Survive Lower Bound",0, 8, 2);
		surviveThresholdLowMSlider.setPreferredSize(new Dimension(200, 20));
		slider_panel.add(surviveThresholdLowMSlider);
		
		surviveThresholdHighMSlider = new MessageSlider("Custom Survive Upper Bound",0, 8,3);
		surviveThresholdHighMSlider.setPreferredSize(new Dimension(200, 20));
		slider_panel.add(surviveThresholdHighMSlider);
		
		speedMSlider = new MessageSlider("Set Speed",10, 1000, 500);
		speedMSlider.setPreferredSize(new Dimension(200, 20));
		slider_panel.add(speedMSlider);
		
		add(slider_panel, BorderLayout.SOUTH);
		
		reset_button = new JButton("Clear Board");
		slider_panel.add(reset_button, BorderLayout.EAST);
		advance_button = new JButton("Advance");
		slider_panel.add(advance_button, BorderLayout.SOUTH);
		random_button = new JButton("Fill Random");
		slider_panel.add(random_button, BorderLayout.SOUTH);
		play_button = new JButton("Start");
		slider_panel.add(play_button, BorderLayout.SOUTH);
		torus_button = new JButton("Turn Torus Mode On");
		slider_panel.add(torus_button, BorderLayout.SOUTH);
	

		/* Add subpanel in south area of layout. */
		add(slider_panel, BorderLayout.SOUTH);
	}
	public void render(boolean array[][], boolean[][] previous) {
		rect.repaint(this.getGraphics(), array,previous);
	}
	public void render(boolean array[][]) {
		rect.repaint(this.getGraphics(),array);
	}
	public void render(boolean array[][], int x, int y) {
		rect.repaint(this.getGraphics(), array, x, y);
	}
	public void addActionListener(ActionListener l) {
		for(Component c: slider_panel.getComponents()) {
			try {
				JButton b = (JButton) c;
				b.addActionListener(l);
			}
			catch (Exception e) {
				//
			}
		}
	}
	public MessageSlider getBoardSizeMSlider() {
		return boardSizeMSlider;
	}
	public MessageSlider getSpeedMSlider() {
		return speedMSlider;
	}
	public MessageSlider getBirthThresholdMSlider() {
		return birthThresholdMSlider;
	}
	public MessageSlider getSurviveThresholdLowMSlider() {
		return surviveThresholdLowMSlider;
	}
	public MessageSlider getSurviveThresholdHighMSlider () {
		return surviveThresholdHighMSlider;
	}
	public JButton getPlayButton() {
		return play_button;
	}
	public void addMouseListener(MouseListener l) {
		rect.addMouseListener(l);
	}
	
	public void addChangeListener(ChangeListener l) {
		for(Component c: slider_panel.getComponents()) {
			MessageSlider s = null;
			try {
				s = (MessageSlider)c;
				s.getSlider().addChangeListener(l);
				}
			catch (Exception e){
				
			}
		}
	}
	
	public JRectangle getBoard() {
		return rect;
	}
}
	
