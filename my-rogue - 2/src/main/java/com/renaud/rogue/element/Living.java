package com.renaud.rogue.element;

import java.util.HashSet;
import java.util.Set;

import com.renaud.rogue.element.projectile.Projectile;
import com.renaud.rogue.game.Game;
import com.renaud.rogue.tools.Point;

public interface Living extends Element {
	boolean isDead();

	void injured(Projectile projectile);

	void injured(Monster projectile);

	default Set<Point> getVisibilityPoints(Game game) {
		return new HashSet<>();
	};
}
