package com.renaud.rogue.tools;

import java.util.HashSet;
import java.util.Set;

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

	public static Set<Point> getSegment(int ax, int ay, int bx, int by) {
		Set<Point> points = new HashSet<>();
		int dx, dy, i, xinc, yinc, cumul, x, y;
		x = ax;
		y = ay;
		dx = bx - ax;
		dy = by - ay;
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
