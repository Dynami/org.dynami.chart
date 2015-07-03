package org.dynami.chart.plot;


import org.dynami.chart.Plottable;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Point;

public class Title extends Plottable {
	private String title = "";
	
	public Title(String title){
		this.title = title;
		setVisible(title != null);
	}

	@Override
	public void paintControl(PaintEvent e) {
		if(isVisible()){
			applyStyle(e.gc);
			
			Point stringGraph = e.gc.stringExtent(title);
			int titleLength = stringGraph.x;
			int x = bounds.width/2-titleLength/2;
			int y = 0;
			e.gc.drawString(title, x, y);
		}
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
		setVisible(title != null);
	}
}
