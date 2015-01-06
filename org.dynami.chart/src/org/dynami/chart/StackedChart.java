package org.dynami.chart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.dynami.chart.plot.Axis;
import org.dynami.chart.plot.Chart;
import org.dynami.chart.plot.Title;
import org.dynami.chart.utils.CLongSet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class StackedChart extends Composite {
	public static final double RANGE_PADDING_COEF = 0.1;
	public static final int SAMPLE_SIZE = 50;
	public static final int PIXELS_PER_UNIT = 8;
	public static final int TICK_UNIT_FACTOR = 4;
	public static final int TICK_PIXEL_SIZE = 4;
	public static final int SPACE = 2;
	
	private final String MAIN;
	private final List<Chart> charts = new ArrayList<>();
	private final Axis xAxis = new Axis("x axis", false);
	private final CLongSet xValues = new CLongSet(SAMPLE_SIZE);
	
	private final Title title = new Title("Chart");
	
	public StackedChart(Composite parent, int style, String mainChart) {
		super(parent, style | SWT.DOUBLE_BUFFERED);
		setLayout(new FillLayout(SWT.VERTICAL));
		MAIN = mainChart;
	    charts.add(new Chart(MAIN, 100));
	     
		addPaintListener((e)->{
			e.gc.setAdvanced(true);
			e.gc.setAntialias(SWT.ON);
			
			double sum = charts.stream().mapToDouble(Chart::getWeight).sum();
        	int plotsNumber = charts.size();
        	int x = PAD.RIGHT.size;
        	int y = PAD.UPPER.size;
        	int width = e.width-PAD.LEFT.size-PAD.RIGHT.size;
        	int height = 0;
        	
        	e.gc.setBackground(getBackground());
        	e.gc.setForeground(getForeground());
        	e.gc.setFont(getFont());
        	
        	e.gc.fillRectangle(0, 0, e.width, e.height);
        	
        	title.setBackground(getBackground());
        	title.setForeground(getForeground());
        	title.setFont(getFont());
        	title.setBounds(new Rectangle(0, PAD.UPPER.size, e.width, e.height));
        	title.paintControl(e);
        	
        	int available_height = e.height-PAD.UPPER.size-PAD.LOWER.size-(PAD.INFRA_PLOT.size*plotsNumber-1);
        	for(Chart c : charts){
        		y += height+PAD.INFRA_PLOT.size;
            	height = (int)((c.getWeight()/sum)*available_height);
        		c.setBounds(new Rectangle(x, y, width, height));
        		c.setXValues(xValues);
        		c.paintControl(e);
        		
        		drawVerticalTitle(e.gc, c.getBounds(), c.getName());
        	}
        	y+=height;
        	xAxis.setBounds(new Rectangle(x, y, width, height));
        	xAxis.paintControl(e);
		});
	}
	
	public Chart getMainChart(){
		return charts.get(0);
	}
	
	public Chart getChart(String key){
		Optional<Chart> optional = charts.stream().filter((g)-> g.getName().equals(key)).findFirst();
		if(optional.isPresent()){
			return optional.get();
		} else {
			return null;
		}
	}
	
	public Collection<Chart> getCharts(){
		return Collections.unmodifiableCollection(charts);
	}
	
	public Chart addNewChart(String name, double weight){
		Chart g = new Chart(name, weight);
		charts.add(g);
		return g;
	}
	
	public void removeAllCharts(){
		charts.removeIf((c)-> !c.getName().equals(MAIN));
	}
	
	public boolean removeChart(String name){
		return charts.removeIf((c)-> c.getName().equals(name));
	}
	
	public CLongSet getXValues() {
		return xValues;
	}

	public static enum PAD {
		INFRA_PLOT(5),
		UPPER(15),
		LOWER(15),
		LEFT(45),
		RIGHT(20);
		
		final int size;
		private PAD(int size){
			this.size= size;
		}
		
		public int val(){
			return size;
		}
	}
	
	private void drawVerticalTitle(GC gc, Rectangle bounds, String title) {
		try {
	        int textWidth = bounds.height;
	        int textHeight = StackedChart.PAD.RIGHT.val();
	        
	        // widen for italic font
	        int margin = textHeight / 10;
	        textWidth += margin;
	
	        /*
	         * create image to draw text. If drawing text on rotated graphics
	         * context instead of drawing rotated image, the text shape becomes a
	         * bit ugly especially with small font with bold.
	         */
	        Image image = new Image(Display.getCurrent(), textWidth, textHeight);
	        GC tmpGc = new GC(image);
	
	        tmpGc.setFont(getFont());
	        tmpGc.setBackground(getBackground());
	        tmpGc.setForeground(getForeground());
	        tmpGc.fillRectangle(image.getBounds());
	        tmpGc.drawText(title, 0, 0);
	
	        // set transform to rotate
	        Transform oldTransform = new Transform(gc.getDevice());
	        gc.getTransform(oldTransform);
	        Transform transform = new Transform(gc.getDevice());
	        transform.translate(0, textWidth);
	        transform.rotate(270);
	        gc.setTransform(transform);
	
	        // draw the image on the rotated graphics context
	        int x = bounds.x-StackedChart.PAD.RIGHT.val();
	        int y = bounds.y;
	
	        gc.drawImage(image, -y, x);
	
	        gc.setTransform(oldTransform);
	
	        // dispose resources
	        tmpGc.dispose();
	        transform.dispose();
	        image.dispose();
		} catch (Exception e) {}
    }
}
