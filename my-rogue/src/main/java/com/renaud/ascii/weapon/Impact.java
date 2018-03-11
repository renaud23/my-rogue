package com.renaud.ascii.weapon;

public class Impact implements Shoot {

	private Weapon weapon;
	private int x;
	private int y;

	public Impact(Weapon weapon, int x, int y) {
		this.weapon = weapon;
		this.x = x;
		this.y = y;
	}

	@Override
	public Weapon getWeapon() {
		return weapon;
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
	public boolean isFinished() {
		return true;
	}

}
