package com.gw.web.common.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class TestUtils {
	
	public static double calc(double f, int c, double rate) {
		BigDecimal  r = new BigDecimal(f).multiply(new BigDecimal(1d+rate).pow(c));
		return r.doubleValue();
	}
	
	public static double calc(int a, int b) {
		BigDecimal  r = new BigDecimal(a).divide(new BigDecimal(b), 4, RoundingMode.HALF_UP);
		return r.doubleValue();
	}

	public static void main(String[] args) {
		
		double t1 = calc(1,12);
		System.out.println(t1);
		double t2 = Math.pow(1.03d, t1)-1d;
		System.out.println(t2);
		
		double t3 = calc(1d, 2, t2 );

		System.out.println(new DecimalFormat("#.0000").format(t3));
	}

}
