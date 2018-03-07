package com.renaud.ascii.figure;

import java.util.Set;

public class Triangle implements Figure {

	private Point a;
	private Point b;
	private Point c;

	public boolean isIn(Point pt) {
		boolean b1, b2, b3;

		b1 = sign(pt, a, b) < 0.0f;
		b2 = sign(pt, b, c) < 0.0f;
		b3 = sign(pt, c, a) < 0.0f;

		return ((b1 == b2) && (b2 == b3));
	}

	public Triangle(Point a, Point b, Point c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	private double sign(Point p1, Point p2, Point p3) {
		return (p1.getX() - p3.getX()) * (p2.getY() - p3.getY()) - (p2.getX() - p3.getX()) * (p1.getY() - p3.getY());
	}

	@Override
	public Set<Point> getPoints() {
		// TODO Auto-generated method stub
		return null;
	}

}
