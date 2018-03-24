package com.renaud.rogue.game.weapon;

import com.renaud.rogue.game.element.Joueur;
import com.renaud.rogue.game.element.Living;
import com.renaud.rogue.game.sequence.Game;

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
