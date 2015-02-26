package org.dynami.chart.plot;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.dynami.chart.ISeries;
import org.dynami.chart.Plottable;
import org.dynami.chart.Range;
import org.dynami.chart.StackedChart;
import org.dynami.chart.ISeries.Type;
import org.dynami.chart.plot.series.BarSeries;
import org.dynami.chart.plot.series.LineSeries;
import org.dynami.chart.plot.series.OHLCSeries;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.widgets.Display;

public class Chart extends Plottable {
	private final String name;
	private double weight = 100;
	private final Map<String, Series> series = new HashMap<>();
	private final Grid grid = new Grid();
	
	public Chart(String name, double weight) {
		this.name = name;
		this.weight = weight;
		setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
	}
	
	public String getName() {
		return name;
	}
	
	public double getWeight() {
		return weight;
	}
	
	protected void setWeight(double weight) {
		this.weight = weight;
	}
	
	@Override
	public void paintControl(PaintEvent e) {
		applyStyle(e.gc);
		e.gc.fillRectangle(bounds);
		e.gc.drawRectangle(bounds);
		
		applyDimensionsTo(grid);
		grid.paintControl(e);
		int xLabel = bounds.x+5; 
		int yLabel = bounds.y;
		for(Series s : series.values()){
			applyDimensionsTo(s);
			s.paintControl(e);
			
			e.gc.setBackground(getBackground());
			e.gc.setForeground(s.getForeground());
			e.gc.drawString(s.getName(), xLabel, yLabel, true);
			xLabel += e.gc.stringExtent(s.getName()).x+5;
		}
	}
	
	public ISeries attachSeries(String name, ISeries.Type type){
		Series s = null;
		if(Type.Line.equals(type)){
			s = new LineSeries(name, type);
		} else if(Type.Bar.equals(type)){
			s = new BarSeries(name, type, true);
		} else if(Type.Ohlc.equals(type)){
			s = new OHLCSeries(name, type);
		}
		assert s == null :"Not implemented type of series";
		series.put(name, s);
		return s;
	}
	
	public void adjustRange(){
		double max = -Double.MAX_VALUE;
		double min = Double.MAX_VALUE;
		for(ISeries s : series.values()){
			max = Math.max(s.max(), max);
			min = Math.min(s.min(), min);
		}
		double pad = Math.abs(max-min)*StackedChart.RANGE_PADDING_COEF;
		max += pad;
		min -= pad;
		setRange(new Range(min, max));
	}
	
	public void adjustMinRange(final double min){
		double max = -Double.MAX_VALUE;
		for(ISeries s : series.values()){
			max = Math.max(s.max(), max);
		}
		double pad = Math.abs(max-min)*StackedChart.RANGE_PADDING_COEF;
		max += pad;
		setRange(new Range(min, max));
	}
	
	public void adjustMaxRange(final double max){
		double min = Double.MAX_VALUE;
		for(ISeries s : series.values()){
			min = Math.min(s.min(), min);
		}
		double pad = Math.abs(max-min)*StackedChart.RANGE_PADDING_COEF;
		min -= pad;
		setRange(new Range(min, max));
	}
	
	public void adjustMaxMinRange(double max, double min){
		setRange(new Range(min, max));
	}
	
	public ISeries getSeries(String name){
		return series.get(name);
	}
	
	public Collection<ISeries> getSeries(){
		return Collections.unmodifiableCollection(series.values());
	}
}
