package com.renaud.rogue.game.element.monster;

public class Ghoul extends Shooter {

	private final static int MIN_GHOUL_XP = 5;

	public Ghoul(int x, int y, int level) {
		super(x, y, MIN_GHOUL_XP * level);

		this.depht = 12;
		this.dephtOfFire = 6;
		this.life = 10;
		this.actionsMax = 2;
		this.isMeleeAttaque = true;
		this.meleeDamage = 8;
	}

	@Override
	public String getName() {
		return "Ghoul";
	}

}
