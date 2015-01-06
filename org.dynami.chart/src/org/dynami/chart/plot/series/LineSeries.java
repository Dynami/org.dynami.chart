package org.dynami.chart.plot.series;

import org.dynami.chart.Sample;
import org.dynami.chart.StackedChart;
import org.dynami.chart.plot.Series;
import org.eclipse.swt.events.PaintEvent;

public class LineSeries extends Series {
	
	public LineSeries(String name, Type type) {
		super(name, type);
	}
	
	@Override
	public void printSeries(PaintEvent e) {
		if(isVisible()){
			int plottable_units = bounds.width/StackedChart.PIXELS_PER_UNIT;
			long[] xes = xValues.toArray();
			int size = xes.length;
			Sample s1, s2;
			for(int i = xes.length-1, d= 0; i >= 0; i--,d++){
				s2 = values.getByX(xes[i]);
				if(s2 == null) continue;
				for(int j=i-1, z=d+1; j >= 0 && z < plottable_units; j--, z++){
					s1 = values.getByX(xes[j]);
					if(s1 != null) {
						int y1 = getYAxis(s1.y());
						int y2 = getYAxis(s2.y());
						
						int x1 = 0, x2 = 0;
						if(plottable_units < size){
							x1 = bounds.x+(plottable_units-z)*StackedChart.PIXELS_PER_UNIT;
							x2 = bounds.x+(plottable_units-d)*StackedChart.PIXELS_PER_UNIT;
						} else {
							x1 = bounds.x+j*StackedChart.PIXELS_PER_UNIT;
							x2 = bounds.x+i*StackedChart.PIXELS_PER_UNIT;
						}
						e.gc.drawLine(x1, y1, x2, y2);
						break;
					}
				}
			}
		}
	}
}
