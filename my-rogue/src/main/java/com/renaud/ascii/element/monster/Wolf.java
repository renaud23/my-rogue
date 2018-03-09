package com.renaud.ascii.element.monster;

import java.util.Random;

import com.renaud.ascii.world.World;

public class Wolf implements Monster {

	Random r = new Random();
	int x;
	int y;
	int speed = 1;
	int nx;
	int ny;
	int dx = 0;
	int dy = 0;

	public Wolf(int x, int y) {
		this.x = x;
		this.y = y;

		dx = rndDir();
		dy = rndDir();
		checkNextDir();
	}

	private int rndDir() {
		int how = r.nextInt(3);
		if (how == 0)
			return 0;
		if (how == 1)
			return 1;
		return -1;
	}

	private void checkNextDir() {
		nx = x + dx * speed;
		ny = y + dy * speed;
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
		if (speed > 0) {

			if (x == nx && y == ny) {
				// System.out.println("on ! " + x + " " + y + " " + nx + " " + ny + " " + (x != nx && y != ny));
				checkNextDir();
			}
			else {
				// System.out.println(x + " " + y + " " + nx + " " + ny);
				if (world.canGo(this, x, y, nx, ny)) {
					for (int i = 0; i < speed; i++) {
						int vx = x - nx;
						vx *= vx;
						int vy = y - ny;
						vy *= vy;
						boolean done = false;
						boolean goDX = vy < vx;
						boolean goDY = !goDX;
						if (goDX) {
							if (world.canGo(x + dx, y)) {
								done = true;
								x += dx;
							}
							else {
								goDY = true;
							}
						}
						if (goDY) {
							if (world.canGo(x, y + dy)) {
								done = true;
								y += dy;
							}
							else {
								if (world.canGo(x + dx, y)) {
									done = true;
									x += dx;
								}
							}
						}
						if (!done) {
							turn();
						}
					}
				}
				else {
					turn();
				}
			}
		}
	}

	public void turn() {

	}

	@Override
	public boolean isIn(int x, int y) {
		return this.x == x && this.y == y;
	}

}
