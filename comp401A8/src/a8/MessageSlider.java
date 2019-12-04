package a8;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class MessageSlider extends JPanel{
	private JLabel message, m2;
	private JSlider slider;
	private int value;
	
	private int id;
	public MessageSlider(String message, int min, int max, int value) {
		setLayout(new BorderLayout());
		slider = new JSlider(min,max);
		slider.setPreferredSize(new Dimension(200, 20));
		slider.setName(message);
		this.message = new JLabel();
		this.message.setText(message);
		this.m2 = new JLabel();
		this.m2.setText("        "+value + "            ");
		
		this.message = new JLabel();
		this.message.setText(message);
		add(this.m2, BorderLayout.EAST);
		add(this.message, BorderLayout.WEST);
		add(slider, BorderLayout.CENTER);
		slider.setValue(value);
		
	}
	public void setValue(int n) {
		value = n;
		this.m2.setText("        "+value + "            ");
	}
	public JSlider getSlider() {
		return slider;
	}
	public int getId() {
		return id;
	}
	
}
