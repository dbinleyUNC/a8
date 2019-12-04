package a8;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class JRectangle extends JPanel{

	    private int numRows, dimensions, cellWidth;
	    public JRectangle (int dimensions, int numRows) {
	    	this.numRows = numRows;
	    	this.dimensions = dimensions;
	    	cellWidth = dimensions/numRows;
	    }
	    public int getDimensions() {
	    	return dimensions;
	    }
	    public int getCellWidth() {
	    	return cellWidth;
	    }
	    public void repaint(Graphics g, boolean[][] array) {
	    	numRows = array.length;
	    	
	    	cellWidth = dimensions/numRows;
	    	if (cellWidth*numRows>dimensions) {
	    		while((Math.abs(dimensions-cellWidth*numRows) >Math.abs(dimensions-(cellWidth+1)*numRows))) {
		    		++cellWidth;
		    	}
	    	}
	    	else {
	    		while((Math.abs(dimensions-cellWidth*numRows) >Math.abs(dimensions-(cellWidth-1)*numRows))) {
		    		--cellWidth;
		    	}
	    	}
	    	for (int i =0; i<numRows; i++) {
	    		for (int j=0; j<numRows; j++) {
	    			g.setColor(Color.BLACK);
	    			if (array[i][j]) {
	    				g.setColor(Color.YELLOW);
	    			}
					g.fillRect(cellWidth*j, cellWidth*i,cellWidth,cellWidth);
	    		}
	    	}
	    }
	    public void repaint(Graphics g, boolean[][] array, boolean[][] previous) {
	    	if (previous == null) {
	    		repaint(g,array);
	    	}
	    	
	    	else {
	    		numRows = array.length;
		    	cellWidth = dimensions/numRows;
		    	for (int i =0; i<numRows; i++) {
		    		for (int j=0; j<numRows; j++) {
		    			g.setColor(Color.BLACK);
		    			if (array[i][j]) {
		    				g.setColor(Color.YELLOW);
		    			}
		    			if (array[i][j]!= previous[i][j]) {
		    				g.fillRect(cellWidth*j, cellWidth*i,cellWidth,cellWidth);
		    			}
		    		}
		    	}
	    	}
	    }
	    public void clear() {
	    	this.getGraphics().clearRect(0, 0, 9000, 9000);
	    }
	    public void repaintBlack(Graphics g, boolean[][] array) {
	    	numRows = array.length;
	    	
	    	cellWidth = dimensions/numRows;
	    	if (cellWidth*numRows>dimensions) {
	    		while((Math.abs(dimensions-cellWidth*numRows) >Math.abs(dimensions-(cellWidth+1)*numRows))) {
		    		++cellWidth;
		    	}
	    	}
	    	else {
	    		while((Math.abs(dimensions-cellWidth*numRows) >Math.abs(dimensions-(cellWidth-1)*numRows))) {
		    		--cellWidth;
		    	}
	    	}
					g.fillRect(0, 0,cellWidth*numRows,cellWidth*numRows);
	    	
	    }
	    
	    public void repaint(Graphics g, boolean[][] array, int i, int j) {
	    	numRows = array.length;
	    	cellWidth = dimensions/numRows;
	    	g.setColor(Color.BLACK);
	    	if (array[i][j]) {
	    		g.setColor(Color.YELLOW);
	    	}
				g.fillRect(cellWidth*j, cellWidth*i,cellWidth,cellWidth);
	    	}
}
