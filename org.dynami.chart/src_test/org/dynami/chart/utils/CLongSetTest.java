package org.dynami.chart.utils;

import java.util.Arrays;

import org.dynami.chart.utils.CLongSet;

public class CLongSetTest {

	public static void main(String[] args) {
		CLongSet set = new CLongSet(5);
		set.add(2);
		System.out.printf("add 2\t%20s\t%3s %3s\n", Arrays.toString(set.toArray()), set.min(), set.max());
		set.add(1);
		System.out.printf("add 1\t%20s\t%3s %3s\n", Arrays.toString(set.toArray()), set.min(), set.max());
		set.add(4);
		System.out.printf("add 4\t%20s\t%3s %3s\n", Arrays.toString(set.toArray()), set.min(), set.max());
		set.add(4);
		System.out.printf("add 4\t%20s\t%3s %3s\n", Arrays.toString(set.toArray()), set.min(), set.max());
		set.add(6);
		System.out.printf("add 6\t%20s\t%3s %3s\n", Arrays.toString(set.toArray()), set.min(), set.max());
		set.add(3);
		System.out.printf("add 3\t%20s\t%3s %3s\n", Arrays.toString(set.toArray()), set.min(), set.max());
		set.add(8);
		System.out.printf("add 8\t%20s\t%3s %3s\n", Arrays.toString(set.toArray()), set.min(), set.max());
		set.add(7);
		System.out.printf("add 7\t%20s\t%3s %3s\n", Arrays.toString(set.toArray()), set.min(), set.max());
		set.add(1);
		System.out.printf("add 1\t%20s\t%3s %3s\n", Arrays.toString(set.toArray()), set.min(), set.max());
		
		//[2, 4, 6, 8]
		System.out.println((set.getIndexOf(2) == 0)?"success":"failed");
		System.out.println((set.getIndexOf(4) == 1)?"success":"failed");
		System.out.println((set.getIndexOf(6) == 2)?"success":"failed");
		System.out.println((set.getIndexOf(8) == 3)?"success":"failed");
		System.out.println((set.getIndexOf(1) == -1)?"success":"failed");
		System.out.println((set.getIndexOf(9) == -1)?"success":"failed");
		System.out.println((set.getIndexOf(7) == -1)?"success":"failed");
	}
}
