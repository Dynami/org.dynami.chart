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
package org.dynami.chart.utils;

import java.util.Arrays;

import org.dynami.chart.Sample;

public class CSampleArray {
	private final Sample[] data;
	public final int length;
	private int cursor = 0;
	
	public CSampleArray(final int length) {
		this(length, null, false);
	}
	
	public CSampleArray(final int length, final Sample fill) {
		this(length, fill, true);
	}
	
	private CSampleArray(final int length, final Sample fill, boolean moveCursorToLength) {
		this.length = length;
		this.data = new Sample[length];
		Arrays.fill(data, fill);
		if(moveCursorToLength) cursor = length-1;
	}
	
	public void clear(){
		Arrays.fill(data, null);
		this.cursor = 0;
	}
	
	public Sample[] toArray(){
		final Sample[] out = new Sample[Math.min(cursor, length)];
		for (int j = 0, i = out.length-1; i >=0 ; i--,j++) {
			out[j] = last(i);
		}
		return out;
	}
 	
	public void add(final Sample v){
		data[(cursor++)%length] = v;
	}
	
	public Sample last(int idx){
		assert idx >= 0;
		return data[(cursor-1-idx)%length];
	}
	
	public Sample last(){
		if((cursor-1)%length >= 0)
			return data[(cursor-1)%length];
		else
			return new Sample(0.0);
	}
	
	public Sample get(final int idx){
		assert idx >= 0;
		return data[(cursor<=length)?idx:(cursor+idx)%length];
	}
	
	public int size(){
		return Math.min(length, cursor);
	}
	
	public double min(){
		double min = Double.MAX_VALUE;
		Sample s;
		for(int i = 0; i < Math.min(size(), length); i++){
			s = get(i);
			for(int j = 0; j < s.getDim(); j++){
				if(!Double.isNaN(s.y(j))){
					min = Math.min(min, s.y(j));
				}
			}
		}
		return min;
	}
	
	public double min(int period){
		assert period > 0: "Period ["+period+"] has to be greater than zero";
		double min = Double.MAX_VALUE;
		Sample d = null;
		int size = Math.min(size(), period);
		for(int i = size-1; i >= 0; i--){
			d = last(i);
			if(d!= null && !Double.isNaN(d.y()) ){
				for(int j = 0; j < d.getDim(); j++){
					if(d.y(j) < min){
						min = d.y(j);
					}
				}
			}
		}
		return min;
	}
	
	public double max(){
		double max = -Double.MAX_VALUE;
		Sample s;
		for(int i = 0; i < Math.min(size(), length); i++){
			s = get(i);
			for(int j = 0; j < s.getDim(); j++){
				if(!Double.isNaN(s.y(j))){
					max = Math.max(max, s.y(j));
				}
			}
		}
		return max;
	}
	
	public double max(int period){
		assert period > 0: "Period ["+period+"] has to be greater than zero";
		double max = -Double.MAX_VALUE;
		Sample d = null;
		int size = Math.min(size(), period);
		for(int i = size-1; i >= 0; i--){
			d = last(i);
			if(d != null && !Double.isNaN(d.y())){
				for(int j = 0; j < d.getDim();j++){
					if(d.y(j) > max){
						max = d.y(j);
					}
				}
			}
		}
		return max;
	}
	
	public Sample getByX(long x){
		if(x < start() || x > end()) 
			return null;
		
		int start = 0;
		int end = size()-1;
		int mean = (end-start)/2+start;
		int count_safe = 0;
		boolean isOdd = mean%2==0;
		while((end-start)>=0 && ++count_safe < 100){
			mean = (end-start)/2+start;
			isOdd = (end-start)%2==0;
			if(isOdd && get(mean).x()==x){
				return get(mean);
			} else if(end - start > 1){
				if(x <= get(mean).x()){
					end = (isOdd)?mean-1:mean;
				} else {
					start = (isOdd)?mean+1:mean;
				}
			} else {
				if(get(end).x()==x)
					return get(end);
				else if(get(start).x() == x)
					return get(start);
				else
					return null;
			}
		}
		return null;
	}
	
	public long end(){
		Sample s = last();
		if(s != null)
			return s.x();
		else
			return Sample.NONE;
	}
	
	public long start(){
		Sample s = get(0);
		if(s != null)
			return s.x();
		else
			return Sample.NONE;
	}
}

