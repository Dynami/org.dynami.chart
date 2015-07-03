package org.dynami.chart.plot;

import java.util.Locale;

import org.dynami.chart.ISeries.Type;
import org.dynami.chart.Plottable;
import org.dynami.chart.StackedChart;
import org.eclipse.swt.events.PaintEvent;

public class Last extends Plottable {
	private final int DIM, SPACE = 2;
	private final int[] lastLable;
	private final Type type;
	private double yValue;
	public Last(Type type) {
		this.type = type;
		DIM = getFont().getFontData()[0].getHeight()/2+SPACE;
		lastLable = new int[]{
				0,DIM, 
				DIM,0, 
				StackedChart.PAD.RIGHT.val(),0,
				StackedChart.PAD.RIGHT.val(),2*DIM, 
				DIM,2*DIM
			};
	}
	
	public void setYValue(double yValue){
		this.yValue = yValue;
	}
	
	@Override
	public void paintControl(PaintEvent e) {
		if(isVisible()){
			applyStyle(e.gc);
			
			int yAxis = getYAxis(yValue);
			e.gc.setBackground( (Type.Line.equals(type))?getForeground():getBackground());
			e.gc.fillPolygon(new int[]{
				lastLable[0]+getBounds().x+getBounds().width,
				lastLable[1]+yAxis-DIM,
				
				lastLable[2]+getBounds().x+getBounds().width,
				lastLable[3]+yAxis-DIM,
				
				lastLable[4]+getBounds().x+getBounds().width,
				lastLable[5]+yAxis-DIM,
				
				lastLable[6]+getBounds().x+getBounds().width,
				lastLable[7]+yAxis-DIM,
				
				lastLable[8]+getBounds().x+getBounds().width,
				lastLable[9]+yAxis-DIM,
			});
			
			e.gc.setForeground( (Type.Line.equals(type))?getBackground():getForeground());
			e.gc.drawText(
					String.format(Locale.getDefault(), "%6.2f", yValue), 
					lastLable[0]+getBounds().x+getBounds().width+DIM, 
					lastLable[1]+yAxis-DIM*2-SPACE,
					true);
		}
	}
}
