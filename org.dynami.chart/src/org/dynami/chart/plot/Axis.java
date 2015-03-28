package org.dynami.chart.plot;

import org.dynami.chart.Plottable;
import org.dynami.chart.StackedChart;
import org.eclipse.swt.events.PaintEvent;

public class Axis extends Plottable {
	private String name;
	private boolean vertical = true;
	
	public Axis(String name) {
		this.name = name;
	}
	
	public Axis(String name, boolean vertical) {
		this.name = name;
		this.vertical = vertical;
	}
	
	@Override
	public void paintControl(PaintEvent e) {
		applyStyle(e.gc);
		
		if(vertical){
			for(int i = 0; i < bounds.height/StackedChart.PIXELS_PER_UNIT; i++){
				
				e.gc.drawLine(
						bounds.x+bounds.width-StackedChart.TICK_PIXEL_SIZE/2, 
						bounds.y+StackedChart.PIXELS_PER_UNIT*i, 
						bounds.x+bounds.width+StackedChart.TICK_PIXEL_SIZE/2, 
						bounds.y+StackedChart.PIXELS_PER_UNIT*i);
			}
		} else {
			
			for(int i = 0; i < bounds.width/StackedChart.PIXELS_PER_UNIT; i++){
				e.gc.drawLine(
						bounds.x+StackedChart.PIXELS_PER_UNIT*i, 
						bounds.y, 
						bounds.x+StackedChart.PIXELS_PER_UNIT*i, 
						bounds.y+StackedChart.TICK_PIXEL_SIZE/2);
				
//				e.gc.drawText(string, x, y);
			}
		}
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isVertical() {
		return vertical;
	}
}
