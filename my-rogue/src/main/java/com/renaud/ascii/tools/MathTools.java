package com.renaud.ascii.tools;

import com.renaud.ascii.figure.Point;

public class MathTools {
	public static int distance(Point a, Point b) {
		int distx = a.getX() - b.getX();
		distx *= distx;
		int disty = a.getY() - b.getY();
		disty *= disty;

		return disty + distx;
	}

	public static int distance(int ax, int ay, int bx, int by) {
		int distx = ax - bx;
		distx *= distx;
		int disty = ay - by;
		disty *= disty;

		return disty + distx;
	}
}
