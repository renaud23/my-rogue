package com.renaud.rogue.weapon;

import com.renaud.rogue.element.Joueur;
import com.renaud.rogue.element.Living;
import com.renaud.rogue.game.Game;

public class NoWeapon implements Weapon {

	private int damage = 0;

	public NoWeapon() {

	}

	@Override
	public int getDepht() {
		return 0;
	}

	public boolean canAim(Joueur joueur, int x, int y) {
		return false;
	}

	@Override
	public void shoot(Game game, int aimx, int aimy) {}

	public int getDamage() {
		return 0;
	}

	@Override
	public String getName() {
		return "no wepon";
	}

	public Living getUser() {
		return null;
	}

}
