package org.dynami.chart.plot;

import org.dynami.chart.ISeries;
import org.dynami.chart.Plottable;
import org.dynami.chart.Sample;
import org.dynami.chart.StackedChart;
import org.dynami.chart.utils.CSampleArray;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.LineAttributes;
import org.eclipse.swt.widgets.Display;

public abstract class Series extends Plottable implements ISeries {
	protected final CSampleArray values = new CSampleArray(StackedChart.SAMPLE_SIZE);
//	protected boolean useXset = false;
	private final Last last;
	private final String name;
	private final Type type;
	private int lineType = SWT.LINE_SOLID;
	private int lineWidth = 1;
	private long count = 0;
	
	protected Series(String name, Type type) {
		this.name = name;
		this.type = type;
		last = new Last(type);
		
		setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
		setBackground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
	}
	
	@Override
	public void paintControl(PaintEvent e) {
		if(isVisible()){
			applyStyle(e.gc);
			e.gc.setLineAttributes(new LineAttributes(lineWidth, SWT.CAP_FLAT, SWT.JOIN_MITER, lineType, null, 0, 10));
			
			printSeries(e);
			
			if(values.size() > 0){
				applyDimensionsTo(last);
				applyStyleTo(last);
				last.setYValue(values.last().y());
				last.paintControl(e);
			}
		}
	}

	@Override
	public void add(Sample sample) {
		if(sample.x() != Sample.NONE){
			xValues.add(sample.x());
			values.add(sample);
		} else {
			add(new Sample(++count, sample.ys()));
		}
	}

	@Override
	public void add(double value) {
		add(new Sample(++count, value));
	}

	@Override
	public double max() {
		return values.max();
	}

	@Override
	public double min() {
		return values.min();
	}

	@Override
	public void setLineStyle(int swt_type) {
		lineType = swt_type;
	}
	
	@Override
	public int getLineStyle() {
		return lineType;
	}
	
	@Override
	public void setLineWidth(int width) {
		this.lineWidth = width;
	}
	
	@Override
	public int getLineWidth() {
		return lineWidth;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public Type getType() {
		return type;
	}
	
	@Override
	public Last getLast() {
		return last;
	}
}
