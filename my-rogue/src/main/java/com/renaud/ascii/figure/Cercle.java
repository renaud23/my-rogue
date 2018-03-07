package com.renaud.ascii.figure;

import java.util.HashSet;
import java.util.Set;

public class Cercle {

	Point centre;
	int rayon;

	public Set<Point> getPoints() {
		Set<Point> points = new HashSet<>();

		int x, y, d;
		x = 0;
		y = rayon;
		d = 5 - 4 * rayon;
		while (x <= y) {
			points.add(new Point(x + centre.getX(), y + centre.getY()));
			points.add(new Point(y + centre.getX(), x + centre.getY()));
			points.add(new Point(-x + centre.getX(), y + centre.getY()));
			points.add(new Point(y + centre.getX(), -x + centre.getY()));

			points.add(new Point(x + centre.getX(), -y + centre.getY()));
			points.add(new Point(-y + centre.getX(), x + centre.getY()));
			points.add(new Point(-x + centre.getX(), -y + centre.getY()));
			points.add(new Point(-y + centre.getX(), -x + centre.getY()));
			if (d > 0) {
				y--;
				d = d - 8 * y;
			}
			x++;
			d = d + 8 * x + 4;

		}

		return points;

	}

	public boolean isIn(Point a) {
		double dx = a.getX() - centre.getX();
		double dy = a.getY() - centre.getY();
		return (dx * dx + dy * dy) <= (rayon * rayon);
	}

	public Cercle(Point centre, int rayon) {
		this.centre = centre;
		this.rayon = rayon;
	}

}
