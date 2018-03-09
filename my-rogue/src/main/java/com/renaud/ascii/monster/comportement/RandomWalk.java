package com.renaud.ascii.monster.comportement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.renaud.ascii.figure.Cercle;
import com.renaud.ascii.figure.Point;
import com.renaud.ascii.monster.element.Comportement;
import com.renaud.ascii.monster.element.Monster;
import com.renaud.ascii.world.World;

public class RandomWalk implements Comportement {

	Random r = new Random();
	int nx;
	int ny;
	Set<Point> already = new HashSet<>();

	Monster monster;

	public RandomWalk(Monster monster) {
		this.monster = monster;
		reset();
	}

	private void checkNextDir(World world) {
		already.clear();
		Random rnd = new Random();
		int ray = monster.getDepth();
		boolean find = false;
		while (!find) {
			Cercle c = new Cercle(monster.getX(), monster.getY(), ray);
			List<Point> points = new ArrayList<>(c.getPoints());

			while (!points.isEmpty()) {
				Point p = points.remove(rnd.nextInt(points.size()));
				if (world.canGo(monster, monster.getX(), monster.getY(), p.getX(), p.getY())) {
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
		for (int h = 0; h < monster.getSpeed(); h++) {
			int dx = 0;
			int dy = 0;
			int best = 999999;

			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					Point tmp = new Point(monster.getX() + i, monster.getY() + j);
					if (already.contains(tmp) || (i == 0 && j == 0) || (i != 0 && j != 0))
						continue;
					if (w.canGo(monster, monster.getX() + i, monster.getY() + j)) {
						int distx = nx - monster.getX() - i;
						distx *= distx;
						int disty = ny - monster.getY() - j;
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
				monster.addX(dx);
				monster.addY(dy);
				already.add(new Point(monster.getX(), monster.getY()));
			}
		}
	}

	@Override
	public void reset() {
		nx = monster.getX();
		ny = monster.getY();
		already.clear();
	}

	@Override
	public void activate(World world) {
		if (monster.getSpeed() > 0) {
			if (nx == monster.getX() && ny == monster.getY()) {
				checkNextDir(world);
			} else {
				move(world);
			}
		}
	}
}
