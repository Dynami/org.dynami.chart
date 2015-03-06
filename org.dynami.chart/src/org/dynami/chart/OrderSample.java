package org.dynami.chart;

public class OrderSample extends Sample {
	public static enum Side {Long, Short};
	public final Side side;
	public OrderSample(long x, double y, Side side){
		super(x,y);
		this.side = side;
	}
}
