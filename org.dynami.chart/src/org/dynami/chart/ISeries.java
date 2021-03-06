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

import org.dynami.chart.plot.Last;
import org.eclipse.swt.events.PaintEvent;

public interface ISeries extends IPlottable {

	public abstract void add(Sample sample);

	public abstract void add(double value);

	public abstract double max();

	public abstract double min();
	
	public void printSeries(PaintEvent e);
	
	public void setLineStyle(int swt_style);
	
	public int getLineStyle();
	
	public void setLineWidth(int width);
	
	public int getLineWidth();
	
	public String getName();
	
	public Type getType();
	
	public Last getLast();
	
	public void clear();
	
	public static enum Type {
		Line , Bar , Ohlc, Order
	}
}