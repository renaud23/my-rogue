package com.renaud.rogue.element.comportement;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.renaud.rogue.element.PNJ;
import com.renaud.rogue.game.Game;
import com.renaud.rogue.tools.Point;

public class GoTo implements Comportement {

    PNJ monster;

    Random r = new Random();
    public int nx;
    public int ny;
    Set<Point> already = new HashSet<>();

    public GoTo(PNJ monster, int nx, int ny) {
	this.monster = monster;
	this.nx = nx;
	this.ny = ny;
    }

    private void move(Game game) {
	if (!isFinished()) {
	    // for (int h = 0; h < monster.getStep(); h++) {
	    int dx = 0;
	    int dy = 0;
	    int best = 999999;

	    for (int i = -1; i <= 1; i++) {
		for (int j = -1; j <= 1; j++) {
		    Point tmp = new Point(monster.getX() + i, monster.getY() + j);
		    if (already.contains(tmp) || (i == 0 && j == 0) || (i != 0 && j != 0))
			continue;
		    if (game.getWorld().canGo(monster.getX() + i, monster.getY() + j)) {
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
		// TODO
	    } else {
		// game .setElement(monster.getX(), monster.getY(), null);
		// monster.addX(dx);
		// monster.addY(dy);
		// game.getWorld().setElement(monster.getX(), monster.getY(), monster);
		game.moveTo(monster, monster.getX() + dx, monster.getY() + dy);
		already.add(new Point(monster.getX(), monster.getY()));
	    }
	    // }
	}
    }

    public boolean isFinished() {
	return monster.getX() == nx && monster.getY() == ny;
    }

    @Override
    public void activate(Game game) {
	move(game);
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
