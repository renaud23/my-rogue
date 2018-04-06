package com.renaud.rogue.game.element.monster;

import com.renaud.rogue.game.element.Monster;
import com.renaud.rogue.game.element.projectile.Projectile;
import com.renaud.rogue.game.weapon.Weapon;
import com.renaud.rogue.game.world.Game;
import com.renaud.rogue.view.drawer.GameConsoleDrawer;

public abstract class AbstractMonster implements Monster {

	protected int x;
	protected int y;
	protected int actions = 3;

	public int actionsMax = 3;
	public int depht = 10;
	public int level = 1;
	public int life = 10;
	public int xp = 2;
	public int meleeDamage = 5;

	public boolean opaque = false;

	public AbstractMonster(int x, int y, int xp) {
		this.x = x;
		this.y = y;
		this.xp = xp;
	}

	@Override
	public void injured(Game game, Weapon weapon) {
		this.life -= weapon.getDamage();
		GameConsoleDrawer.success(getName() + " reçoit un coup de " + weapon.getName() + " " + this.life + "  life");
		if (isDead()) {
			weapon.getUser().kill(this);
			game.removeMonster(this);
			game.bloodify(x, y);
		}
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
		return depht;
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

	@Override
	public int getXp() {
		return xp * this.level;
	}

}
