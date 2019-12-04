package a8;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class GameOfLifeModel {
	private List<GameOfLifeObserver> observers;
	private boolean[][] boardSpots;
	private boolean [][] previousSpots;
	
	private int birthThreshold, surviveThresholdLow,surviveThresholdHigh; 
	
	private boolean torusOn;
	private int waitTime;
	public GameOfLifeModel() {
		observers = new ArrayList<GameOfLifeObserver>();
		boardSpots = new boolean[50][50];
		fillRandomBoard();
		previousSpots = null;
		torusOn = false;
		waitTime = 500;
		birthThreshold = 3;
		surviveThresholdLow = 2;
		surviveThresholdHigh = 3;
	}
	
	public boolean[][] getBoard() {
		return boardSpots;
	} 
	public boolean[][] getPreviousBoard() {
		return previousSpots;
	}
	public int getBirthThreshold() {
		return birthThreshold;
	}
	public int getSurviveThresholdLow() {
		return surviveThresholdLow;
	}
	public int getSurviveThresholdHigh() {
		return surviveThresholdHigh;
	}
	public boolean getTorusOn() {
		return torusOn;
	}
	public int getWaitTime() {
		return waitTime;
	}
	
	public void setBirthThreshold(int n) {
		 birthThreshold = n;
	}
	public void setSurviveThresholdHigh(int n) {
		 surviveThresholdHigh = n;
	}
	public void setSurviveThresholdLow(int n) {
		 surviveThresholdLow= n;
	}
	public void setBoard(boolean[][] array) {
		int n = -1;
		if (previousSpots == null) {
			n= -100;
		}
		previousSpots = boardSpots;
		boardSpots = array;
		notifyObservers(-1,-1);
	}
	private void updatePrevious() {
		previousSpots = new boolean[boardSpots.length][boardSpots[0].length];
		for (int i = 0; i< boardSpots.length; i++) {
			for (int j = 0; j< boardSpots[i].length; j++) {
				previousSpots[i][j] = boardSpots[i][j];
			}
		}
	}
	public void setWaitTime(int num) {
		if (num<10 || num>1000) {
			throw new IllegalArgumentException("time entered not valid");
		}
		waitTime = num;
	}
	
	public void toggleSpot(int x, int y) {
		previousSpots = boardSpots;
		boardSpots[x][y]= !boardSpots[x][y];
		notifyObservers(x,y);
	}

	public void toggleTorusOn() {
		torusOn = !torusOn;
	}
	
	public void changeDimensions (int n) {
		previousSpots = null;
		boardSpots = new boolean[n][n];
		
	}
	public void clearBoard() {
		previousSpots = boardSpots;
		boardSpots = new boolean[boardSpots.length][boardSpots[0].length];
		notifyObservers(-1,-1);
	}
	public void fillRandomBoard() {
		clearBoard();
		for (int i = 0; i<boardSpots.length; i++) {
			for (int j = 0; j<boardSpots.length; j++) {
				if (Math.random() >.6) {
					boardSpots[i][j] = true;
				}
				else {
					boardSpots [i][j] = false;
				}
			}
		}
		notifyObservers(-1,-1);
	}
	public void advanceSpots() {
		boolean finalArray[][] = new boolean[boardSpots.length][boardSpots[0].length];
		for (int i = 0; i< boardSpots.length; i++) {
			for (int j = 0; j< boardSpots[i].length; j++) {
				finalArray[i][j] = boardSpots[i][j];
			}
		}
		for (int i = 0; i< boardSpots.length; i++) {
			for (int j = 0; j< boardSpots[i].length; j++) {
				int count = 0;
				if (getTorusOn()) {
					count = calculateTorusCount(i,j);
				}
				else {
					count = calculateNormalCount(i,j);
				}
				
				if (!boardSpots[i][j] && count == birthThreshold) {
					finalArray[i][j] = true;
				}
				else if (boardSpots[i][j] && (count< surviveThresholdLow
						|| count>surviveThresholdHigh))  {
					finalArray[i][j] = false;
				}
			}
		}
		setBoard(finalArray);
	}
	private int calculateNormalCount(int i, int j) {
		int count = 0;
		if (i>0) {
			if (boardSpots[i-1][j]) count++;
			if (j>0 && boardSpots[i-1][j-1]) count++;
			if (j<boardSpots[i].length-1 &&boardSpots[i-1][j+1]) count++;
		}
		if (j>0) {
			if (boardSpots[i][j-1]) count++;
			if (i < boardSpots.length-1 && boardSpots[i+1][j-1]) count++;
		}
		if (i<boardSpots.length-1) {
			if (boardSpots[i+1][j]) count++;
			if (j<boardSpots[i].length-1 && boardSpots[i+1][j+1]) count++;
		}
		if (j<boardSpots[i].length-1 && boardSpots[i][j+1]) count++;	
		return count;
		
	}
	private int calculateTorusCount(int i, int j) {
		int ia = i+1;
		int ib = i-1;
		int ja = j+1;
		int jb = j-1;	
		int count = 0;
		
		if (i==0 || j ==0 || j ==boardSpots.length-1 || i == boardSpots.length-1) {
			if (i== 0) {
				ib = boardSpots.length-1;
			}
			if (i == boardSpots.length-1) {
				ia = 0;
			}
			if (j == 0) {
				jb = boardSpots.length-1;
			}
			if (j==boardSpots.length-1) {
				ja = 0;
			}
			if (boardSpots[ib][j]) count++;
			if (boardSpots[ib][jb]) count++;
			if (boardSpots[ib][ja]) count++;
			
			if (boardSpots[i][jb]) count++;
			if (boardSpots[ia][jb]) count++;
			
			if (boardSpots[ia][j]) count++;
			if (boardSpots[ia][ja]) count++;
			
			if (boardSpots[i][ja]) count++;
			
			return count;
		}
		else {
			return calculateNormalCount(i,j);
		}
	}
	public void addObserver(GameOfLifeObserver o) {
		observers.add(o);
	}
	
	public void removeObserver(GameOfLifeObserver o) {
		observers.remove(o);
	}
	
	private void notifyObservers(int x, int y) {
		for (GameOfLifeObserver o : observers) {
			o.update(x,y);
		}
	}
}
