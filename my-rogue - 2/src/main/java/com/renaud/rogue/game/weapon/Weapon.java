package com.renaud.rogue.weapon;

import com.renaud.rogue.element.Joueur;
import com.renaud.rogue.element.Living;
import com.renaud.rogue.sequence.Game;

public interface Weapon {

	boolean canAim(Joueur joueur, int x, int y);

	Living getUser();

	int getDepht();

	void shoot(Game game, int aimx, int aimy);

	String getName();

	default int getDamage() {
		return 0;
	};

}
