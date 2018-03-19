package com.renaud.rogue.weapon;

import com.renaud.rogue.element.Living;
import com.renaud.rogue.game.Game;

public interface Weapon {

	Living getUser();

	int getDepht();

	void shoot(Game game, int aimx, int aimy);

	String getName();

	default int getDamage() {
		return 0;
	};

}
