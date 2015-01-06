package org.dynami.chart.utils;

import org.dynami.chart.Sample;
import org.dynami.chart.utils.CSampleArray;

public class CSampleArrayTest {

	public static void main(String[] args) {
		//1325494800000, 1325498400000, 1325505600000, 1325509200000, 1325512800000, 1325516400000, 1325520000000, 1325523600000, 1325581200000, 1325584800000, 1325592000000, 1325595600000, 1325599200000, 1325602800000, 1325606400000, 1325610000000, 1325667600000, 1325671200000, 1325678400000, 1325682000000, 1325685600000, 1325689200000, 1325692800000, 1325696400000, 1325754000000, 1325757600000, 1325764800000, 1325768400000, 1325772000000, 1325775600000, 1325779200000, 1325782800000, 1325840400000, 1325844000000
		CSampleArray a = new CSampleArray(100);
		Sample[] samples = parse("[#1325494800000 [15119.5, 15204.6, 15093.2, 15182.1], #1325498400000 [15183.6, 15289.5, 15170.0, 15289.0], #1325458800000 [15287.8, 15301.5, 15262.8, 15298.8], #1325505600000 [15304.7, 15330.5, 15284.7, 15314.6], #1325509200000 [15314.8, 15380.7, 15314.2, 15380.5], #1325512800000 [15380.4, 15399.5, 15365.4, 15385.5], #1325516400000 [15385.5, 15429.2, 15383.1, 15422.0], #1325520000000 [15423.5, 15456.2, 15409.8, 15430.2], #1325523600000 [15432.3, 15448.6, 15417.6, 15446.4], #1325581200000 [15506.1, 15623.3, 15478.1, 15600.1], #1325584800000 [15599.8, 15616.2, 15559.8, 15604.3], #1325545200000 [15605.8, 15607.5, 15485.1, 15545.2], #1325592000000 [15547.0, 15583.9, 15515.0, 15569.0], #1325595600000 [15568.9, 15571.3, 15500.5, 15507.1], #1325599200000 [15507.0, 15533.7, 15465.2, 15519.5], #1325602800000 [15520.9, 15585.6, 15515.6, 15582.6], #1325606400000 [15582.8, 15659.0, 15571.2, 15647.2], #1325610000000 [15649.3, 15675.1, 15647.9, 15659.0], #1325667600000 [15560.9, 15632.3, 15350.8, 15410.1], #1325671200000 [15411.5, 15522.3, 15381.5, 15461.7], #1325631600000 [15462.6, 15500.3, 15439.9, 15447.7], #1325678400000 [15446.4, 15461.1, 15400.1, 15416.7], #1325682000000 [15416.7, 15446.8, 15398.1, 15412.9], #1325685600000 [15414.2, 15414.9, 15358.5, 15363.8]]");
		
		for(Sample s:samples){
			a.add(s);
		}
		
		System.out.println((a.getByX(1325498400000L).y() == 15183.6)?"success":"failed");
		System.out.println((a.getByX(1325545200000L).y() == 15605.8)?"success":"failed");
		System.out.println((a.getByX(1325685600000L).y() == 15414.2)?"success":"failed");
		
//		a.add(new Sample(1325494800000L, 1));
//		a.add(new Sample(1325498400000L, 2));
//		a.add(new Sample(1325505600000L, 3));
//		a.add(new Sample(1325509200000L, 4));
//		a.add(new Sample(1325512800000L, 7));
//		a.add(new Sample(1325516400000L, 8));
		
//		System.out.println((a.last().y() == 8)?"success":"failed");
//		System.out.println((a.last(1).y() == 7)?"success":"failed");
//		System.out.println((a.get(1).y() == 2)?"success":"failed");
//		System.out.println((a.get(0).y() == 1)?"success":"failed");
		
//		System.out.println((a.getByX(1325505600000L).y() == 3)?"success":"failed");
//		System.out.println((a.getByX(1325494800000L).y() == 1)?"success":"failed");
//		System.out.println((a.getByX(1325498400000L).y() == 2)?"success":"failed");
//		System.out.println((a.getByX(1325509200000L).y() == 4)?"success":"failed");
//		System.out.println((a.getByX(6) == null)?"success":"failed");
//		System.out.println((a.getByX(0) == null)?"success":"failed");
//		System.out.println((a.getByX(8) == null)?"success":"failed");
		
		
	}
	
	//[#1325494800000 [15119.5, 15204.6, 15093.2, 15182.1], #1325498400000 [15183.6, 15289.5, 15170.0, 15289.0], #1325458800000 [15287.8, 15301.5, 15262.8, 15298.8], #1325505600000 [15304.7, 15330.5, 15284.7, 15314.6], #1325509200000 [15314.8, 15380.7, 15314.2, 15380.5], #1325512800000 [15380.4, 15399.5, 15365.4, 15385.5], #1325516400000 [15385.5, 15429.2, 15383.1, 15422.0], #1325520000000 [15423.5, 15456.2, 15409.8, 15430.2], #1325523600000 [15432.3, 15448.6, 15417.6, 15446.4], #1325581200000 [15506.1, 15623.3, 15478.1, 15600.1], #1325584800000 [15599.8, 15616.2, 15559.8, 15604.3], #1325545200000 [15605.8, 15607.5, 15485.1, 15545.2], #1325592000000 [15547.0, 15583.9, 15515.0, 15569.0], #1325595600000 [15568.9, 15571.3, 15500.5, 15507.1], #1325599200000 [15507.0, 15533.7, 15465.2, 15519.5], #1325602800000 [15520.9, 15585.6, 15515.6, 15582.6], #1325606400000 [15582.8, 15659.0, 15571.2, 15647.2], #1325610000000 [15649.3, 15675.1, 15647.9, 15659.0], #1325667600000 [15560.9, 15632.3, 15350.8, 15410.1], #1325671200000 [15411.5, 15522.3, 15381.5, 15461.7], #1325631600000 [15462.6, 15500.3, 15439.9, 15447.7], #1325678400000 [15446.4, 15461.1, 15400.1, 15416.7], #1325682000000 [15416.7, 15446.8, 15398.1, 15412.9], #1325685600000 [15414.2, 15414.9, 15358.5, 15363.8]]
	public static Sample[] parse(String input){
		String tmp = input.substring(1, input.length()-1);
		String[] rawSamples = tmp.split("#");
		Sample[] samples = new Sample[rawSamples.length];
		for(int i = 0; i < rawSamples.length; i++){
			String rrs = rawSamples[i].replace(',', ' ').replace(']', ' ').replace('[', ' ').trim();
			String[] token = rrs.split("  ");
			//System.out.println(Arrays.toString(token));
			if(!"".equals(token[0])){
				long t = Long.parseLong(token[0].trim());
				double[] yes = new double[token.length-1];
				for(int j = 1; j < token.length; j++){
					yes[j-1] = Double.parseDouble(token[j].trim());
				}
				samples[i] = new Sample(t, yes);				
			}
		}
		return samples;
	}

}