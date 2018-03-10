package com.renaud.ascii.monster.comportement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.renaud.ascii.figure.Cercle;
import com.renaud.ascii.figure.Point;
import com.renaud.ascii.monster.element.Monster;
import com.renaud.ascii.world.World;

public class RandomWalk implements Comportement {

	int nx;
	int ny;

	Monster monster;

	GoTo goTo;

	public RandomWalk(Monster monster) {
		this.monster = monster;
		goTo = new GoTo(monster, monster.getX(), monster.getY());
		reset();
	}

	private void checkNextDir(World world) {
		int dx = monster.getX();
		int dy = monster.getY();
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
					dx = p.getX();
					dy = p.getY();
					break;
				}
			}
			if (!find) {
				ray--;
			}
		}
		goTo.reset(dx, dy);
	}

	@Override
	public void reset() {
		nx = monster.getX();
		ny = monster.getY();
	}

	@Override
	public void activate(World world) {
		if (monster.getSpeed() > 0) {
			if (goTo.isArrived()) {
				checkNextDir(world);
			} else {
				goTo.activate(world);
			}
		}
	}
}
