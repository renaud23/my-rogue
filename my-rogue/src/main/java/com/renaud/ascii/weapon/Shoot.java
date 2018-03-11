package com.renaud.ascii.weapon;

public interface Shoot {
	boolean isFinished();

	default Weapon getWeapon() {
		return null;
	};

	default int getX() {
		return 0;
	};

	default int getY() {
		return 0;
	};
}
