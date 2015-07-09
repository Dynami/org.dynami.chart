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
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class StackedChart extends Composite {
	public static final double RANGE_PADDING_COEF = 0.1;
	public static final int SAMPLE_SIZE = 100;
	public static final int PIXELS_PER_UNIT = 8;
	public static final int TICK_UNIT_FACTOR = 4;
	public static final int TICK_PIXEL_SIZE = 4;
	public static final int SPACE = 2;
	private boolean forceFontToSubPlottable = false;
	
	private final String MAIN;
	private final Title title = new Title(null);
	private final List<Chart> charts = new ArrayList<>();
	private final CLongSet xValues = new CLongSet(SAMPLE_SIZE);
	
	private final Axis xAxis = new Axis("x axis", false){
		public void paintControl(PaintEvent e) {
			final int SIZE = xValues.size();
			final int PLOTTABLE_UNITS = bounds.width/StackedChart.PIXELS_PER_UNIT;
			final int LABEL_SIZE = e.gc.stringExtent(String.valueOf(xValues.last())).x;
			for(int i = SIZE-1; i >= 0; i--){
				final float factor = ((LABEL_SIZE+SPACE)/PIXELS_PER_UNIT)+1;
				
				final int x = (PLOTTABLE_UNITS >= SIZE)?
						PAD.LEFT.size+i*StackedChart.PIXELS_PER_UNIT:
						PAD.LEFT.size+(i-SIZE+PLOTTABLE_UNITS)*StackedChart.PIXELS_PER_UNIT;
				
				if(i%factor == 0){
					e.gc.drawString(
							String.valueOf(xValues.get(i)),
							x, 
							bounds.y,
							true);
				}
				
				if(x < PAD.LEFT.size){
					break;
				}
			}
		};
	};
	
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
        	int x = PAD.LEFT.size;
        	int y = SPACE*2+(title.isVisible()?title.getFont().getFontData()[0].getHeight():0);
        	int width = e.width-PAD.LEFT.size-PAD.RIGHT.size;
        	int height = 0;
        	
        	e.gc.setBackground(getBackground());
        	e.gc.setForeground(getForeground());
        	e.gc.setFont(getFont());
        	
        	e.gc.fillRectangle(0, 0, e.width, e.height);
        	
        	title.setBackground(getBackground());
        	title.setForeground(getForeground());
        	title.setFont(getFont());
        	title.setBounds(new Rectangle(0, y, e.width, e.height));
        	title.paintControl(e);
        	
        	int available_height = e.height-y-PAD.LOWER.size-(PAD.INFRA_PLOT.size*plotsNumber-1);
        	for(Chart c : charts){
        		y += height+PAD.INFRA_PLOT.size;
            	height = (int)((c.getWeight()/sum)*available_height);
        		c.setBounds(new Rectangle(x, y, width, height));
        		c.setXValues(xValues);
        		if(forceFontToSubPlottable){ 
        			c.setFont(getFont());
        		}
        		c.paintControl(e);
        		
        		drawVerticalTitle(e.gc, c.getBounds(), c.getName());
        	}
        	y+=height;
        	
        	xAxis.setBounds(new Rectangle(x, y, width, height));
        	xAxis.setXValues(xValues);
        	e.gc.setBackground(getBackground());
        	e.gc.setForeground(getForeground());
        	e.gc.setFont(getFont());
        	xAxis.paintControl(e);
		});
	}
	
	public Title getTitle(){
		return title;
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
	
	public void setFont(Font font, boolean forceFontToSubPlottable) {
		super.setFont(font);
		this.forceFontToSubPlottable = forceFontToSubPlottable;
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
		//UPPER(15),
		LOWER(20),
		LEFT(20),
		RIGHT(50);
		
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
	        int textHeight = StackedChart.PAD.LEFT.val();
	        
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
	        int x = bounds.x-StackedChart.PAD.LEFT.val();
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
