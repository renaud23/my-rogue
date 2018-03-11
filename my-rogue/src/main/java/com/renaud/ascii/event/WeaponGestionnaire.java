package com.renaud.ascii.event;

import com.renaud.ascii.weapon.Weapon;
import com.renaud.ascii.world.World;

public class WeaponGestionnaire implements OnEventAction {

	World world;
	Weapon weapon;
	int aimX;
	int aimY;

	boolean finished;
	boolean spacePressed;

	public WeaponGestionnaire(World world) {
		this.world = world;
		this.weapon = world.getJoueur().getWeapon();
	}

	@Override
	public void reset() {
		finished = false;
		spacePressed = false;
		aimX = world.getJoueur().getX();
		aimY = world.getJoueur().getY();
	}

	public boolean activate() {
		if (spacePressed) {
			finished = true;
			weapon.shoot();
			return true;
		}

		return false;
	}

	@Override
	public void keyUpReleased() {
		if (canAimBy(0, -1)) {
			aimY--;
		}
	}

	@Override
	public void keyDownReleased() {
		if (canAimBy(0, 1)) {
			aimY++;
		}
	}

	@Override
	public void keyLeftReleaseded() {
		if (canAimBy(-1, 0)) {
			aimX--;
		}
	}

	@Override
	public void keyRightRealesed() {
		if (canAimBy(1, 0)) {
			aimX++;
		}
	}

	@Override
	public void spaceReleaseded() {
		spacePressed = true;
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

	public int getAimX() {
		return aimX;
	}

	public int getAimY() {
		return aimY;
	}

	private boolean canAimBy(int dx, int dy) {
		int nx = aimX + dx;
		int ny = aimY + dy;

		int varx = Math.abs(nx - world.getJoueur().getX());
		int vary = Math.abs(ny - world.getJoueur().getY());

		if (varx <= weapon.getDepht() && vary <= weapon.getDepht()) {
			return true;
		}

		return false;
	}

}
