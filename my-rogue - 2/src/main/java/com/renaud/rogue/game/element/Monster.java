package com.renaud.rogue.game.element;

import com.renaud.rogue.game.element.monster.Shooter;

public interface Monster extends Living, TurnPlay {

	void addX(int dx);

	void addY(int dy);

	int getDepht();

	String getName();

	default int getMeleeDamage() {
		return 0;
	}

	public static class Factory {

		public static Monster createGhool(int x, int y) {
			Shooter m = new Shooter(x, y);
			m.depht = 12;
			m.dephtOfFire = 6;
			m.life = 10;
			m.actionsMax = 2;
			m.isMeleeAttaque = true;
			m.meleeDamage = 2;
			m.xp = 4;
			m.name = "Ghoull";

			return m;
		}
	}
}
