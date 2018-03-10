package com.renaud.ascii.monster.element;

import com.renaud.ascii.monster.comportement.Comportement;
import com.renaud.ascii.monster.comportement.RandomWalk;
import com.renaud.ascii.world.World;

public class Wolf implements Monster {

	int x;
	int y;
	int speed = 1;
	int depth = 15;

	private Comportement walk;
	private Comportement hunt;

	public Wolf(int x, int y) {
		this.x = x;
		this.y = y;
		walk = new RandomWalk(this);
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

	@Override
	public boolean isJoueur() {
		return false;
	}

	@Override
	public boolean isOpaque() {
		return true;
	}
}
