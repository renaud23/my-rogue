package com.renaud.ascii.element;

import java.util.List;

import com.renaud.ascii.figure.Point;
import com.renaud.ascii.world.World;

public class Joueur implements Element {

	private int x;
	private int y;
	private ChampVision vision;
	private World memory; // mémoire du joueur

	public Joueur(int wl, int wh) {
		vision = new ChampVision(this, 20, Math.PI / 2.0);
		memory = new World(wl, wh);
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
	public List<Point> getVisibilityPoints(World world) {
		List<Point> points = vision.getPoints(world);
		for (Point p : points) {
			memory.setTile(p.getX(), p.getY(), world.getTile(p.getX(), p.getY()));
		}

		return points;
	}

	public int getMemory(int i, int j) {
		return memory.getTile(i, j);
	}

}
