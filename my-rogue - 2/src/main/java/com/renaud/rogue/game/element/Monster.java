package com.renaud.rogue.game.element;

public interface Monster extends Living, TurnPlay {

	void addX(int dx);

	void addY(int dy);

	int getDepht();

	String getName();

	int getXp();

	default int getMeleeDamage() {
		return 0;
	}
}
