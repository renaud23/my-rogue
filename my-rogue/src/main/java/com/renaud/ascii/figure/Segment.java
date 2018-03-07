package com.renaud.ascii.figure;

import java.util.HashSet;
import java.util.Set;

public class Segment implements Figure {

	private Point a;
	private Point b;

	public Segment(Point a, Point b) {
		this.a = a;
		this.b = b;
	}

	public Set<Point> getPoints() {
		Set<Point> points = new HashSet<>();
		int dx, dy, i, xinc, yinc, cumul, x, y;
		x = a.getX();
		y = a.getY();
		dx = b.getX() - a.getX();
		dy = b.getY() - a.getY();
		xinc = (dx > 0) ? 1 : -1;
		yinc = (dy > 0) ? 1 : -1;
		dx = Math.abs(dx);
		dy = Math.abs(dy);

		points.add(new Point(x, y));

		if (dx > dy) {
			cumul = dx / 2;
			for (i = 1; i <= dx; i++) {
				x += xinc;
				cumul += dy;
				if (cumul >= dx) {
					cumul -= dx;
					y += yinc;
				}
				points.add(new Point(x, y));
			}
		}
		else {
			cumul = dy / 2;
			for (i = 1; i <= dy; i++) {
				y += yinc;
				cumul += dx;
				if (cumul >= dy) {
					cumul -= dy;
					x += xinc;
				}
				points.add(new Point(x, y));
			}
		}

		return points;
	}
}
