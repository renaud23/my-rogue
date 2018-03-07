package com.renaud.ascii.figure;

import java.util.Set;

public class Point implements Figure {

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}

	int x;
	int y;

	@Override
	public int hashCode() {
		return x + 10000000 * y;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Point) {
			if (((Point) o).x == x && ((Point) o).y == y) {
				return true;
			}
		}
		return false;
	}

	public Point(int x, int y) {

		this.x = x;
		this.y = y;
	}

	public Point() {

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Set<Point> getPoints() {
		// TODO Auto-generated method stub
		return null;
	}

}
