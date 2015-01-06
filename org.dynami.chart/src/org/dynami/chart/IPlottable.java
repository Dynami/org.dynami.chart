package org.dynami.chart;

import org.dynami.chart.utils.CLongSet;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

public interface IPlottable extends PaintListener {
	
	public boolean isVisible();
	public void setVisible(boolean visible);
	
	public Rectangle getBounds();
	public void setBounds(Rectangle bounds);
	
	public Color getForeground();
	public void setForeground(Color color);
	
	public Color getBackground();
	public void setBackground(Color color);
	
	public Font getFont();
	public void setFont(Font font);
	
	public Range getRange();
	public void setRange(Range range);
	
	public CLongSet getXValues();
	public void setXValues(CLongSet xValues);
	
	public int getXAxis(final double value);
	public int getYAxis(final double value);
	public double getYValue(final int yPixels);
	public long getXValue(final int xPixels);
	
	public default void applyDimensionsTo(IPlottable plottable){
		plottable.setBounds(getBounds());
		plottable.setRange(getRange());
		plottable.setXValues(getXValues());
	}
	
	public default void applyStyleTo(IPlottable plottable){
		plottable.setBackground(getBackground());
		plottable.setForeground(getForeground());
		plottable.setFont(getFont());
	}
	
	public default void applyStyle(final GC gc){
		gc.setForeground(getForeground());
		gc.setBackground(getBackground());
		gc.setFont(getFont());
	}
	
	public void dispose();
}
