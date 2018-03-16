package com.renaud.rogue.tools;

public class Point {

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}

	public int x;
	public int y;

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

}
