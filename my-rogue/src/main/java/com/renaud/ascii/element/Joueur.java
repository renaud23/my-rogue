package com.renaud.ascii.element;

import java.util.ArrayList;
import java.util.List;

import com.renaud.ascii.dongeon.Level;
import com.renaud.ascii.figure.Point;

public class Joueur implements Element {

	private int x;
	private int y;
	private ChampVision vision;
	private Level memory; // mémoire du joueur

	private List<Point> lastComputed = new ArrayList<>();

	public Joueur(int wl, int wh) {
		vision = new ChampVision(this, 20, Math.PI / 2.0);
		memory = new Level(wl, wh);
	}

	public void turnLeft() {
		vision.addAngle(Math.PI / 24.0);
	}

	public void turnRight() {
		vision.addAngle(-Math.PI / 24.0);
	}

	public void goUp() {
		y--;
	}

	public void goDown() {
		y++;
	}

	public void goRight() {
		x++;
	}

	public void goLeft() {
		x--;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public List<Point> getVisibilityPoints(Level level) {
		lastComputed = vision.getPoints(level);
		for (Point p : lastComputed) {
			memory.set(p.getX(), p.getY(), level.get(p.getX(), p.getY()));
		}

		return lastComputed;
	}

	public List<Point> getLastVisibilityPoints() {
		return lastComputed;
	}

	public int getMemory(int i, int j) {
		return memory.get(i, j);
	}

}
