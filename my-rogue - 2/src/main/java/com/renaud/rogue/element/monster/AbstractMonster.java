package com.renaud.rogue.element.monster;

import com.renaud.rogue.element.Monster;
import com.renaud.rogue.element.projectile.Projectile;
import com.renaud.rogue.game.Game;
import com.renaud.rogue.weapon.Weapon;

public abstract class AbstractMonster implements Monster {

	protected int x;
	protected int y;
	protected int actions = 3;

	public int actionsMax = 3;
	public int depth = 10;
	public int level = 1;
	public int life = 10;
	public int xp = 2;
	public int meleeDamage = 5;

	public boolean opaque = false;

	@Override
	public void injured(Game game, Weapon weapon) {
		this.life -= weapon.getDamage();
		if (isDead()) {
			weapon.getUser().winXp(this.xp);
			game.removeMonster(this);
			game.bloodify(x, y);
		}
		System.out.println(getName() + " re�oit un coup de " + weapon.getName() + " " + this.life + "  life");
	}

	@Override
	public boolean isDead() {
		return life <= 0;
	}

	@Override
	public void injured(Game game, Projectile projectile) {}

	@Override
	public void injured(Game game, Monster projectile) {}

	@Override
	public int getMeleeDamage() {
		return this.meleeDamage * this.getLevel();
	}

	@Override
	public boolean isOpaque() {
		return opaque;
	}

	/* */

	@Override
	public void startTurn() {
		actions = actionsMax;
	}

	@Override
	public boolean turnIsEnd() {
		return actions <= 0 || isDead();
	}

	/* */

	@Override
	public int getDepht() {
		return depth;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public void addX(int dx) {
		x += dx;
	}

	@Override
	public void addY(int dy) {
		y += dy;
	}

	@Override
	public int getLevel() {
		return this.level;
	}

}
