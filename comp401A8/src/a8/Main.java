package a8;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		GameOfLifeModel model = new GameOfLifeModel();
		GameOfLifeView view = new GameOfLifeView();
		GameOfLifeController controller = new GameOfLifeController(model, view);
		JFrame main_frame = new JFrame();
		main_frame.setTitle("Conway's Game of Life");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main_frame.setContentPane(view);
		main_frame.setSize(1000,1000);
		main_frame.setVisible(true);
		main_frame.setResizable(false);
		try {
			Thread.sleep(500);
		}
		catch(Exception e) {
			
		}
		view.render(model.getBoard());
		
	}
	
}
