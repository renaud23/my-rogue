package com.renaud.rogue.world;

import java.io.PrintStream;
import java.util.Set;

import com.renaud.rogue.element.Element;
import com.renaud.rogue.element.PNJ;
import com.renaud.rogue.tools.MathTools;
import com.renaud.rogue.tools.Point;

public class World {

    private int width;
    private int height;
    private int size;
    private Dungeon dungeon;

    public World(int width, int height) {
	this.width = width;
	this.height = height;
	this.size = this.width * this.height;
	this.dungeon = SmoothLevelProvider.newInstance(width, height).setNbStep(5).build();
    }

    public Point peekEmptyPlace() {
	return dungeon.peekRandomOne(Tile.FLOOR);
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public Tile getTile(int i, int j) {
	return dungeon.getTile(i, j);
    }

    public Tile getTile(int i) {
	return dungeon.getTile(i);
    }

    public void setElement(int i, int j, Element element) {
	this.dungeon.getTile(i, j).setElement(element);
    }

    public boolean canGo(int x, int y) {
	if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight()) {
	    return false;
	}
	return getTile(x, y).canWalkOn();
    }

    public boolean canGo(int ax, int ay, int bx, int by) {
	Set<Point> points = MathTools.getSegment(ax, ay, bx, by);
	for (Point p : points) {
	    if (p.x < 0 || p.y < 0 || p.x >= getWidth() || p.y >= getHeight()) {
		continue;
	    }
	    if ((p.x == ax && p.y == ay) || (p.x == bx && p.y == by)) {
		continue;
	    }
	    if (!this.getTile(p.x, p.y).canWalkOn()) {
		return false;
	    }
	}
	return true;
    }

    public boolean canSee(PNJ pnj, Element cible) {
	int dist = MathTools.distance(pnj.getX(), pnj.getY(), cible.getX(), cible.getY());
	dist *= dist;
	if (dist > pnj.getDepht() * pnj.getDepht())
	    return false;
	Set<Point> points = MathTools.getSegment(pnj.getX(), pnj.getY(), cible.getX(), cible.getY());
	for (Point p : points) {
	    if ((p.x == pnj.getX() && p.y == pnj.getY()) || (p.x == cible.getX() && p.y == cible.getY())) {
		continue;
	    }
	    if (!this.getTile(p.x, p.y).canSeeThrought()) {
		return false;
	    }

	}
	return true;
    }

    public void print(PrintStream out) {
	for (int i = 0; i < size; i++) {
	    Tile tile = dungeon.getTile(i);
	    if (tile.isEmpty()) {
		out.print(tile.getTile());
	    } else {
		out.print(tile.getElement().getTile().getTile());
	    }

	    if ((i % width) == (width - 1)) {
		out.println();
	    }
	}
    }
}
