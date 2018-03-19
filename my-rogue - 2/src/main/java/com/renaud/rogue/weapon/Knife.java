package com.renaud.rogue.weapon;

import com.renaud.rogue.element.Joueur;
import com.renaud.rogue.element.Living;
import com.renaud.rogue.game.Game;
import com.renaud.rogue.world.Tile;

public class Knife implements Weapon {

	private Living user;
	private int damage = 5;

	public Knife(Living user) {
		this.user = user;
	}

	@Override
	public int getDepht() {
		return 1;
	}

	@Override
	public void shoot(Game game, int aimx, int aimy) {
		Tile tile = game.getWorld().getTile(aimx, aimy);
		if (!tile.isEmpty()) {
			if (tile.getElement() instanceof Joueur) {
				System.out.println("vous vous infligé un sépuku rituel !");
			}
			else
				if (tile.getElement() instanceof Living) {
					((Living) tile.getElement()).injured(game, this);
				}
		}
	}

	public int getDamage() {
		return this.damage * this.user.getLevel();
	}

	@Override
	public String getName() {
		return "knife";
	}

	public Living getUser() {
		return user;
	}

}
