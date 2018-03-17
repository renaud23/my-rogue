package com.renaud.rogue.element.comportement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.renaud.rogue.element.Monster;
import com.renaud.rogue.game.Game;
import com.renaud.rogue.tools.MathTools;
import com.renaud.rogue.tools.Point;
import com.renaud.rogue.world.World;

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
	int ray = monster.getDepht();
	boolean find = false;
	while (!find) {
	    List<Point> points = new ArrayList<>(MathTools.getCercle(monster.getX(), monster.getY(), ray));
	    Collections.shuffle(points, new Random());
	    while (!points.isEmpty()) {
		Point p = points.remove(0);
		if (world.canGo(monster.getX(), monster.getY(), p.getX(), p.getY())) {
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
	// System.out.println(monster.getX() + " " + monster.getY() + " " + dx + " " +
	// dy);
	goTo.reset(dx, dy);
    }

    @Override
    public void reset() {
	nx = monster.getX();
	ny = monster.getY();
    }

    @Override
    public void activate(Game game) {
	// if (!monster.turnIsEnd()) {
	if (goTo.isFinished()) {
	    checkNextDir(game.getWorld());
	    this.activate(game);
	} else {
	    goTo.activate(game);
	}
	// }
    }

    @Override
    public boolean isFinished() {
	return false;
    }
}
