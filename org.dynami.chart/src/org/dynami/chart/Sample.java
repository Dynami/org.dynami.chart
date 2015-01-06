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

import java.util.Arrays;

public class Sample {
	public static final int NONE = 0;
	private final long xvalue;
	private final double[] yvalue;
	private final int dim;
	private int main = 0;
	
	public Sample(long x, double...y){
		this.xvalue = x;
		this.yvalue = y;
		this.dim = y.length;
	}
	
	public Sample(double...y){
		this.xvalue = NONE;
		this.yvalue = y;
		this.dim = y.length;
	}
	
	public long x(){ 
		return xvalue;
	}
	
	public double y() {
		return yvalue[main];
	};
	
	public double y(int idx){
		assert yvalue != null :"Y value not defined";
		assert idx >= 0 && idx < yvalue.length : "Idx out of range "+idx+" [0.."+(yvalue.length-1)+"]";
		return yvalue[idx];
	}
	
	public double[] ys(){
		return yvalue;
	}
	
	@Override
	public String toString() {
		return "#"+xvalue+" "+Arrays.toString(yvalue);
	}
	
	public int getDim() {
		return dim;
	}
	
	public Sample setMain(int main){
		this.main = main;
		return this;
	}
}
