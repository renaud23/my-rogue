package com.renaud.ascii.monster.element;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.renaud.ascii.figure.Cercle;
import com.renaud.ascii.figure.Point;
import com.renaud.ascii.monster.comportement.RandomWalk;
import com.renaud.ascii.world.World;

public class Wolf implements Monster {

	int x;
	int y;
	int speed = 1;
	int depth = 15;

	Random r = new Random();
	int nx;
	int ny;
	Set<Point> already = new HashSet<>();

	private Comportement walk;

	public Wolf(int x, int y) {
		this.x = x;
		this.y = y;
		nx = x;
		ny = y;

		walk = new RandomWalk(this);
	}

	private void checkNextDir(World world) {
		already.clear();
		Random rnd = new Random();
		int ray = depth;
		boolean find = false;
		while (!find) {
			Cercle c = new Cercle(x, y, ray);
			List<Point> points = new ArrayList<>(c.getPoints());

			while (!points.isEmpty()) {
				Point p = points.remove(rnd.nextInt(points.size()));
				if (world.canGo(this, x, y, p.getX(), p.getY())) {
					find = true;
					nx = p.getX();
					ny = p.getY();
					break;
				}
			}
			if (!find) {
				ray--;
			}
		}
	}

	private void move(World w) {
		for (int h = 0; h < speed; h++) {
			int dx = 0;
			int dy = 0;
			int best = 999999;

			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					Point tmp = new Point(x + i, y + j);
					if (already.contains(tmp) || (i == 0 && j == 0) || (i != 0 && j != 0))
						continue;
					if (w.canGo(this, x + i, y + j)) {
						int distx = nx - x - i;
						distx *= distx;
						int disty = ny - y - j;
						disty *= disty;
						int dist = distx + disty;
						if (dist < best) {
							best = dist;
							dx = i;
							dy = j;
						}
					}
				}
			}
			if (dx == 0 && dy == 0) {
				checkNextDir(w);
				// move(w);
			} else {
				x += dx;
				y += dy;
				already.add(new Point(x, y));
			}
		}
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void activate(World world) {
		walk.activate(world);
	}

	@Override
	public boolean isIn(int x, int y) {
		return this.x == x && this.y == y;
	}

	@Override
	public int getDepth() {
		return depth;
	}

	@Override
	public void addX(int dx) {
		x += dx;

	}

	@Override
	public void addY(int dy) {
		y += dy;
	}

	@Override
	public int getSpeed() {
		return speed;
	}

}
