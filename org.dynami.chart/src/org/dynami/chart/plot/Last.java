package org.dynami.chart.plot;

import java.util.Locale;

import org.dynami.chart.ISeries.Type;
import org.dynami.chart.Plottable;
import org.dynami.chart.StackedChart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

public class Last extends Plottable {
	private final Color borderColor = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
	private int DIM; // = StackedChart.PAD.RIGHT.val();
	private final int[] lastLabel = new int[10];
	private final int[] polygon = new int[10];
	
	private final Type type;
	private double yValue;
	public Last(Type type) {
		this.type = type;
		DIM = getFont().getFontData()[0].getHeight()/2+StackedChart.SPACE;
		lastLabel[0] = 0;
		lastLabel[1] = DIM; 
		
		lastLabel[2] = DIM;
		lastLabel[3] = 0; 
		
		lastLabel[4] = StackedChart.PAD.RIGHT.val();
		lastLabel[5] = 0;
		
		lastLabel[6] = StackedChart.PAD.RIGHT.val();
		lastLabel[7] = 2*DIM; 
		
		lastLabel[8] = DIM;
		lastLabel[9] = 2*DIM;
	}
	
	private static int setupLast(final int[] lastLabel, int fontSize){
		int DIM = fontSize/2+StackedChart.SPACE;
		lastLabel[0] = 0;
		lastLabel[1] = DIM; 
		
		lastLabel[2] = DIM;
		lastLabel[3] = 0; 
		
		lastLabel[4] = StackedChart.PAD.RIGHT.val();
		lastLabel[5] = 0;
		
		lastLabel[6] = StackedChart.PAD.RIGHT.val();
		lastLabel[7] = 2*DIM; 
		
		lastLabel[8] = DIM;
		lastLabel[9] = 2*DIM;
		return DIM;
	}
	
	public void setYValue(double yValue){
		this.yValue = yValue;
	}
	
	@Override
	public void paintControl(PaintEvent e) {
		if(isVisible()){
			applyStyle(e.gc);
			
			final int yAxis = getYAxis(yValue);
			final String value = String.format(Locale.getDefault(), "%6.2f", yValue);
			final FontData fd = getFont().getFontData()[0];
			int fontSize = fd.getHeight();
			
			while(true){
				int yDim = e.gc.stringExtent(value).x+StackedChart.PAD.INFRA_PLOT.val();
				if(yDim >= StackedChart.PAD.RIGHT.val()){
					fd.setHeight(--fontSize);
					e.gc.setFont(new Font(Display.getDefault(), fd));
				} else {
					break;
				}
			}
			
			DIM = setupLast(lastLabel, fontSize);
			polygon[0] = lastLabel[0]+getBounds().x+getBounds().width;
			polygon[1] = lastLabel[1]+yAxis-DIM;
				
			polygon[2] = lastLabel[2]+getBounds().x+getBounds().width;
			polygon[3] = lastLabel[3]+yAxis-DIM;
				
			polygon[4] = lastLabel[4]+getBounds().x+getBounds().width;
			polygon[5] = lastLabel[5]+yAxis-DIM;
				
			polygon[6] = lastLabel[6]+getBounds().x+getBounds().width;
			polygon[7] = lastLabel[7]+yAxis-DIM;
				
			polygon[8] = lastLabel[8]+getBounds().x+getBounds().width;
			polygon[9] = lastLabel[9]+yAxis-DIM;
			
			e.gc.setBackground( (Type.Line.equals(type))?getForeground():getBackground());
			e.gc.fillPolygon(polygon);
			
			e.gc.setForeground(borderColor);
			e.gc.drawPolygon(polygon);
			e.gc.drawText(
					value,
					lastLabel[0]+getBounds().x+getBounds().width+DIM, 
					lastLabel[1]+yAxis-(DIM*2),
					true);
		}
	}
}
