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
package org.dynami.chart.plot;

import org.dynami.chart.Plottable;
import org.dynami.chart.StackedChart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.LineAttributes;
import org.eclipse.swt.widgets.Display;

public class Grid extends Plottable {
	
	public Grid() {
	}
	
	private Axis[] yAxes = new Axis[]{
		new Axis("y axix")
	};
	
	@Override
	public void paintControl(PaintEvent e) {
		for(Axis ax : yAxes){
			applyDimensionsTo(ax);
			ax.paintControl(e);
		}
		if(isVisible()){
			e.gc.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
			e.gc.setLineAttributes(new LineAttributes(1, SWT.CAP_FLAT, SWT.JOIN_MITER, SWT.LINE_DASH, null, 0, 10));
			for(int i = 1; i <= bounds.height/(StackedChart.PIXELS_PER_UNIT*StackedChart.TICK_UNIT_FACTOR); i++){
				e.gc.drawLine(
						bounds.x+1, 
						bounds.y+StackedChart.PIXELS_PER_UNIT*i*StackedChart.TICK_UNIT_FACTOR, 
						bounds.x+getBounds().width-1, 
						bounds.y+StackedChart.PIXELS_PER_UNIT*i*StackedChart.TICK_UNIT_FACTOR);
			}
		}
	}
	
	public Axis getYAxis(){
		return yAxes[0];
	}
	
	public Axis getYAxis(int idx){
		assert idx >= 0 && idx < yAxes.length : "Idx param ["+idx+"] out of range [0.."+(yAxes.length-1)+"]";
		return yAxes[idx];
	}
	
	@Override
	public void dispose() {
		for(Axis a:yAxes){
			a.dispose();
		}
		super.dispose();
	}
}
