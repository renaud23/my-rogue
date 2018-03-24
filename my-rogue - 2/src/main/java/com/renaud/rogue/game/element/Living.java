package com.renaud.rogue.game.element;

import java.util.HashSet;
import java.util.Set;

import com.renaud.rogue.game.element.projectile.Projectile;
import com.renaud.rogue.game.sequence.Game;
import com.renaud.rogue.game.tools.Point;
import com.renaud.rogue.game.weapon.Weapon;

public interface Living extends Element {

	boolean isDead();

	default void winXp(int xp) {};

	default int getLevel() {
		return 1;
	}

	default void injured(Game game, Projectile projectile) {};

	default void injured(Game game, Monster projectile) {};

	default void injured(Game game, Weapon weapon) {};

	default Set<Point> getVisibilityPoints(Game game) {
		return new HashSet<>();
	};
}
