package org.dynami.chart;

import org.dynami.chart.utils.CLongSet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

public abstract class Plottable implements IPlottable {
	protected boolean visible = true;
	protected Rectangle bounds;
	protected Range range = new Range(0, 100);
	protected CLongSet xValues;
	protected Color foreground = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
	protected Color background = Display.getDefault().getSystemColor(SWT.COLOR_WHITE);
	protected Font font = Display.getDefault().getSystemFont();
	
	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
	}

	@Override
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	@Override
	public Color getForeground() {
		return foreground;
	}

	@Override
	public void setForeground(Color color) {
		this.foreground = color;
	}

	@Override
	public Color getBackground() {
		return background;
	}

	@Override
	public void setBackground(Color color) {
		this.background = color;
	}

	@Override
	public Font getFont() {
		return font;
	}

	@Override
	public void setFont(Font font) {
		this.font = font;
	}

	@Override
	public Range getRange() {
		return range;
	}

	@Override
	public void setRange(Range range) {
		this.range = range;
	}
	
	@Override
	public void setXValues(CLongSet xValues) {
		this.xValues = xValues;
	}
	
	@Override
	public CLongSet getXValues() {
		return xValues;
	}

	@Override
	public void dispose() {
		if(!foreground.isDisposed()){
			foreground.dispose();
		}
		if(!background.isDisposed()){
			background.dispose();
		}
		if(!font.isDisposed()){
			font.dispose();
		}
	}
	
	public int getXAxis(final double value){
		if(Double.isNaN(value)){
			return (int)(bounds.x+bounds.width-((0-xValues.min())/(xValues.max()-xValues.min()))*bounds.width);
		}
		return (int)(bounds.x+bounds.width-((value-xValues.min())/(xValues.max()-xValues.min()))*bounds.width);
	}
	
	public int getYAxis(final double value){
		if(Double.isNaN(value)){
			return (int)(bounds.y+bounds.height);
		}
		return (int)(bounds.y+bounds.height-(((value-range.lower)/(range.upper-range.lower))*bounds.height));
	}
	
	public double getYValue(final int yPixels){
		double dataCoordinate = (bounds.height - yPixels+bounds.y) / (double) bounds.height
                * (range.upper - range.lower) + range.lower;
		return dataCoordinate;
	}
	
	public long getXValue(final int xPixels){
		int plotted = bounds.width/StackedChart.PIXELS_PER_UNIT;
		int idx = (xPixels-bounds.x)/StackedChart.PIXELS_PER_UNIT;
		int pos = plotted-idx-1;
		if(plotted <= xValues.size() && pos >= 0 && pos < xValues.size()){
			return xValues.last(pos);
		} else if(plotted > xValues.size() && idx >= 0 && idx < xValues.size()){
			return xValues.get(idx);
		} else {
			return 0;
		}
	}
}
