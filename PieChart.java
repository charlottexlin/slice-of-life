package sliceoflife;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JComponent;
/**
 * A GUI component that is a pie chart, used to display daily slices.
 * Adapted from code found here: https://www.tutorialspoint.com/javaexamples/gui_piechart.htm
 * 
 * @author https://www.tutorialspoint.com/javaexamples/gui_piechart.htm
 * @author Charlotte Lin made some adjustments to better suit the needs of my project
 */
@SuppressWarnings("serial")
public class PieChart extends JComponent {
	private Slice[] slices;
	private Rectangle size;
	
	/**
	 * Constructor for pie chart
	 * 
	 * @param slices array of slices to include in the pie chart
	 */
	public PieChart(ArrayList<Slice> slices, Rectangle size) {
		this.slices = slices.toArray(new Slice[0]);
		this.size = size;
	}
	
	public void paint(Graphics g) {
		drawPie((Graphics2D) g, size, slices);
	}
	
	/**
	 * Draw the chart based on the given parameters
	 * 
	 * @param g graphics object used for rendering
	 * @param area rectangular area to draw the chart within
	 * @param slices array of slices to include in the pie chart
	 */
	public void drawPie(Graphics2D g, Rectangle area, Slice[] slices) {
		// find total value of all slices
		double total = 0.0D;
		for (int i = 0; i < slices.length; i++) {
			total += slices[i].value;
		}
		
		// draw arcs proportional to slice values
		double curValue = 0.0D;
		int startAngle = 0;
		for (int i = 0; i < slices.length; i++) {
			startAngle = (int) (curValue * 360 / total);
			int arcAngle = (int) (slices[i].value * 360 / total);
			g.setColor(slices[i].color);
			g.fillArc(area.x, area.y, area.width, area.height, startAngle, arcAngle);
			curValue += slices[i].value;
		}
	}
}

class Slice {
	double value;
	Color color;
	
	/**
	 * Constructor for Slice
	 * 
	 * @param value value for this slice
	 * @param color color to draw this slice
	 */
	public Slice(double value, Color color) {  
		  this.value = value;
	      this.color = color;
	}
	
	/**
	 * Update the value of this slice by adding to it.
	 * 
	 * @param value amount to increase this slice's value by (negative value to decrease)
	 */
	public void updateValue(double value) {
		this.value += value;
	}
	
	/**
	 * Set the value of this slice.
	 * 
	 * @param value number to set this slice's value to
	 */
	public void setValue(double value) {
		this.value = value;
	}
	
	
	/**
	 * Return the value of this slice.
	 * 
	 * @return value of this slice
	 */
	public double getValue() {
		return value;
	}
	
	/**
	 * Return the color of this slice.
	 * 
	 * @return color of this slice
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Returns a string version of this slice, for debugging purposes.
	 * 
	 * @return a string representation of this slice.
	 */
	public String toString() {
		return this.value + " " + this.color;
	}
}