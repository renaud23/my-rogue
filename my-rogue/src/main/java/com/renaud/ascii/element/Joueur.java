package com.renaud.ascii.element;

import java.util.ArrayList;
import java.util.List;

import com.renaud.ascii.dongeon.Level;
import com.renaud.ascii.figure.Point;
import com.renaud.ascii.weapon.Weapon;
import com.renaud.ascii.world.World;

public class Joueur implements Element {

	private int x;
	private int y;
	private Weapon weapon;
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

	public List<Point> getVisibilityPoints(World world) {
		lastComputed = vision.getPoints(world);
		for (Point p : lastComputed) {
			memory.set(p.getX(), p.getY(), world.getTile(p.getX(), p.getY()));
		}

		return lastComputed;
	}

	public List<Point> getLastVisibilityPoints() {
		return lastComputed;
	}

	public int getMemory(int i, int j) {
		return memory.getTile(i, j);
	}

	@Override
	public boolean isIn(int x, int y) {
		return x == this.x && y == this.y;
	}

	@Override
	public boolean isJoueur() {
		return true;
	}

	public Weapon getWeapon() {
		return weapon;
	}

}
