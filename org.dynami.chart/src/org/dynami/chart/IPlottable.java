/*
 * Copyright 2014 Alessandro Atria
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dynami.chart;

import org.dynami.chart.utils.CLongSet;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

public interface IPlottable extends PaintListener {
	
//	public void forceFontToSubPlottable(boolean force);
//	public boolean isFontForced();
	
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
