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
import java.util.Iterator;

public class CLongSet implements Iterable<Long> {
	private final long[] data;
	public final int dimension;
	private int cursor = 0;
	
	public CLongSet(final int length) {
		this.dimension = length;
		data = new long[length];
		Arrays.fill(data, Long.MAX_VALUE);
	}
	
	public void clear(){
		Arrays.fill(data, Long.MAX_VALUE);
		this.cursor = 0;
	}
	
	public void add(final long v){
		if(last() < v && getIndexOf(v) == -1){
			data[(cursor++)%dimension] = v;
		}
	}
	
	public long[] toArray(){
		int size = size();
		long[] out = new long[size];
		for(int i = 0; i < size; i++){
			out[i] = get(i);
		}
		return out;
	}
	
	public long last(int idx){
		assert idx >= 0;
		return data[(cursor-1-idx)%dimension];
	}
	
	public long last(){
		if((cursor-1)%dimension >= 0)
			return data[(cursor-1)%dimension];
		else
			return 0;
	}
	
	public long get(final int idx){
		assert idx >= 0;
		return data[(cursor<=dimension)?idx:(cursor+idx)%dimension];
	}
	
	public long min(){
		return get(0);
	}
	
	public int size(){
		return Math.min(dimension, cursor);
	}
	
	public int dimension(){
		return dimension;
	}
	
	public long max(){
		return last();
	}
	
	public int getIndexOf(long x){
		if(x < min() || x > max()) 
			return -1;
		
		int start = 0;
		int end = size()-1;
		int mean = (end-start)/2+start;
		int count_safe = 0;
		boolean isOdd = mean%2==0;
		while((end-start)>=0 && ++count_safe < 100){
			mean = (end-start)/2+start;
			isOdd = (end-start)%2==0;
			if(isOdd && get(mean)==x){
				return mean;
			} else if(end - start > 1){
				if(x <= get(mean)){
					end = (isOdd)?mean-1:mean;
				} else {
					start = (isOdd)?mean+1:mean;
				}
			} else {
				if(get(end)==x)
					return end;
				else if(get(start) == x)
					return start;
				else
					return -1;
			}
		}
		return -1;
	}

	@Override
	public Iterator<Long> iterator() {
		return new Iterator<Long>() {
			private int cursor = 0;
			private int size = size();
			@Override
			public boolean hasNext() {
				return (cursor < size);
			}
			
			@Override
			public Long next() {
				return get(cursor++);
			}
		};
	}
}

