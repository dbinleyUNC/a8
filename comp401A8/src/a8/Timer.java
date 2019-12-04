package a8;


public class Timer extends Thread {
	private boolean done, started;
	private GameOfLifeModel model;
	private GameOfLifeController controller;
	public Timer(GameOfLifeModel model, GameOfLifeController c) {
		this.model = model;
		this.controller = c;
		done = false;
		started = false;
	}

	public void halt() {
		done = true;
		controller.update(-10,1);
	}
	public boolean isRunning() {
		if (!started) {
			return false;
		}
		else {
			return !done;
		}
	}
	public void run() {
		started = true;
		while (!done) {
			try {
				model.advanceSpots();
				//model.setBoard(controller.modifyArray(model.getBoard(),true));
				Thread.sleep(model.getWaitTime());
			} catch (InterruptedException e) {
			}
	}
}
}
