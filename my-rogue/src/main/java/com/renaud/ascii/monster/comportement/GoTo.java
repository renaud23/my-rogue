package com.renaud.ascii.monster.comportement;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.renaud.ascii.figure.Point;
import com.renaud.ascii.monster.element.Monster;
import com.renaud.ascii.world.World;

public class GoTo implements Comportement {

	Monster monster;

	Random r = new Random();
	int nx;
	int ny;
	Set<Point> already = new HashSet<>();

	public GoTo(Monster monster, int nx, int ny) {
		this.monster = monster;
		this.nx = nx;
		this.ny = ny;
	}

	private void move(World w) {
		if (!isFinished()) {
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

				} else {
					monster.addX(dx);
					monster.addY(dy);
					already.add(new Point(monster.getX(), monster.getY()));
				}
			}
		}
	}

	public boolean isFinished() {
		return monster.getX() == nx && monster.getY() == ny;
	}

	@Override
	public void activate(World world) {
		move(world);
	}

	@Override
	public void reset() {
	}

	public void reset(int x, int y) {
		nx = x;
		ny = y;
		already.clear();
	}

}
