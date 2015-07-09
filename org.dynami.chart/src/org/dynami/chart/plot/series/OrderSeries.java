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
package org.dynami.chart.plot.series;

import org.dynami.chart.OrderSample;
import org.dynami.chart.OrderSample.Side;
import org.dynami.chart.StackedChart;
import org.dynami.chart.plot.Series;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

public class OrderSeries extends Series {
	private Color longColor = Display.getDefault().getSystemColor(SWT.COLOR_GREEN);
	private Color shortColor = Display.getDefault().getSystemColor(SWT.COLOR_RED);
	public OrderSeries(String name, Type type) {
		super(name, type);
	}
	
	public void setLongColor(Color longColor){
		this.longColor = longColor;
	}
	
	public void setShortColor(Color shortColor){
		this.shortColor = shortColor;
	}

	@Override
	public void printSeries(PaintEvent e) {
		int plottable_units = bounds.width/StackedChart.PIXELS_PER_UNIT;
		long[] xes = xValues.toArray();
		int size = xes.length;
		
		OrderSample s;
		for(int i = size-1, d = 0; i >= 0; i--,d++){
			s = (OrderSample)values.getByX(xes[i]);
			if(s != null){
				int x = 0;
				if(plottable_units < size){
					x = getBounds().x+(plottable_units-d)*StackedChart.PIXELS_PER_UNIT;
				} else {
					x = getBounds().x+(i)*StackedChart.PIXELS_PER_UNIT;
				}
				int y = getYAxis(s.y());
				
				drawSeriesSymbol(e.gc, x, y, 5, s.side);
			}
		}
	}
	
	public void drawSeriesSymbol(GC gc, int x, int y, int symbolSize, Side side) {
        int oldAntialias = gc.getAntialias();
        gc.setAntialias(SWT.ON);
        Color color = (Side.Long.equals(side))?longColor:shortColor;
        Color oldForeground = gc.getForeground();
        gc.setForeground(color);
        Color oldBackground = gc.getBackground();
        gc.setBackground(color);
        if(Side.Long.equals(side)){
            int[] triangleArray = { x, y - symbolSize, x + symbolSize,
                    y + symbolSize, x - symbolSize, y + symbolSize };
            gc.fillPolygon(triangleArray);
        } else if(Side.Short.equals(side)){
            int[] invertedTriangleArray = { x, y + symbolSize, x + symbolSize,
                    y - symbolSize, x - symbolSize, y - symbolSize };
            gc.fillPolygon(invertedTriangleArray);
        }
        gc.setAntialias(oldAntialias);
        gc.setBackground(oldBackground);
        gc.setForeground(oldForeground);
    }
}
