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
import org.eclipse.swt.events.PaintEvent;

public class BarSeries extends Series {
	boolean zeroAligned = true;
	public BarSeries(String name, Type type) {
		super(name, type);
	}
	
	public BarSeries(String name, Type type, boolean zeroAligned) {
		super(name, type);
		this.zeroAligned = zeroAligned;
	}

	@Override
	public void printSeries(PaintEvent e) {
		if(isVisible()){
			if(zeroAligned && range.lower > 0){
				range.lower = 0;
			}
			
			if(zeroAligned && range.upper < 0){
				range.upper = 0;
			}
			
			int plottable_units = bounds.width/StackedChart.PIXELS_PER_UNIT;
			long[] xes = xValues.toArray();
			int size = xes.length;
			int zeroY = getYAxis(0);
			Sample s;
			for(int i = size-1, d = 0; i >= 0; i--,d++){
				s = values.getByX(xes[i]);
				if(s != null){
					int x = 0;
					if(plottable_units < size){
						x = getBounds().x+(plottable_units-d)*StackedChart.PIXELS_PER_UNIT;
					} else {
						x = getBounds().x+(i)*StackedChart.PIXELS_PER_UNIT;
					}
					e.gc.fillRectangle(x-(StackedChart.PIXELS_PER_UNIT-2)/2, zeroY, (StackedChart.PIXELS_PER_UNIT-StackedChart.SPACE), -(int)(((s.y())/(range.upper-range.lower))*bounds.height));
					e.gc.drawRectangle(x-(StackedChart.PIXELS_PER_UNIT-2)/2, zeroY, (StackedChart.PIXELS_PER_UNIT-StackedChart.SPACE), -(int)(((s.y())/(range.upper-range.lower))*bounds.height));						
				}
			}
		}
	}
}
