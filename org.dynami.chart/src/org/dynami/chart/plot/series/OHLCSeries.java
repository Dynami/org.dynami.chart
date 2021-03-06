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

import org.dynami.chart.Sample;
import org.dynami.chart.StackedChart;
import org.dynami.chart.plot.Series;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.widgets.Display;

public class OHLCSeries extends Series {
	
	public OHLCSeries(String name, Type type) {
		super(name, type);
		setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
		setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
	}
	
	public static final int OPEN = 0;
	public static final int HIGH = 1;
	public static final int LOW = 2;
	public static final int CLOSE = 3;
	
	@Override
	public void printSeries(PaintEvent e) {
		if(isVisible()){
			int plottable_units = bounds.width/StackedChart.PIXELS_PER_UNIT;
			long[] xes = xValues.toArray();
			int size = xes.length;
			Sample s;
			for(int i = size-1, d = 0; i >= 0; i--,d++){
				s = values.getByX(xes[i]);
				if(s != null){
					int oY = getYAxis(s.y(OPEN));
					int hY = getYAxis(s.y(HIGH));
					int lY = getYAxis(s.y(LOW));
					int cY = getYAxis(s.y(CLOSE));
					
					int x = 0;
					if(plottable_units < size){
						x = bounds.x+(plottable_units-d)*StackedChart.PIXELS_PER_UNIT;
					} else {
						x = bounds.x+(i)*StackedChart.PIXELS_PER_UNIT;
					}
					
					e.gc.setForeground(getForeground());
					e.gc.drawLine(x, hY, x, lY);
					
					if(s.y(CLOSE) >= s.y(OPEN)){
						e.gc.setBackground(getBackground());
					} else {
						e.gc.setBackground(getForeground());
					}
					
					e.gc.fillRectangle(
							x-(StackedChart.PIXELS_PER_UNIT-StackedChart.SPACE)/2, 
							cY, 
							(StackedChart.PIXELS_PER_UNIT-StackedChart.SPACE), 
							-(int)(cY-oY));
					
					e.gc.drawRectangle(
							x-(StackedChart.PIXELS_PER_UNIT-StackedChart.SPACE)/2, 
							cY, 
							(StackedChart.PIXELS_PER_UNIT-StackedChart.SPACE), 
							-(int)(cY-oY));
					
				} else {
					System.out.println("Null value for "+xes[i]);
					System.out.println("-----------------------");
				}
			}
		}
	}
}
