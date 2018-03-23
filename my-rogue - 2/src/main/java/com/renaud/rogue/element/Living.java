package com.renaud.rogue.element;

import java.util.HashSet;
import java.util.Set;

import com.renaud.rogue.element.projectile.Projectile;
import com.renaud.rogue.sequence.Game;
import com.renaud.rogue.tools.Point;
import com.renaud.rogue.weapon.Weapon;

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
