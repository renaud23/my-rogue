package com.renaud.rogue.layout.loot.item;

import com.renaud.rogue.game.weapon.MeleeWeapon;
import com.renaud.rogue.game.weapon.NoWeapon;
import com.renaud.rogue.game.weapon.RankedWeapon;
import com.renaud.rogue.game.weapon.Weapon;
import com.renaud.rogue.game.world.Game;

public class EquipeWeaponAction implements ItemLayoutAction {

	private Game game;

	public EquipeWeaponAction(Game game) {
		this.game = game;
	}

	@Override
	public void doIt(ItemLayout u, int i, int j) {
		Weapon weapon = (Weapon) u.getItem();
		Weapon old = null;
		if (weapon instanceof RankedWeapon) {
			old = game.getJoueur().getRankedWeapon();
			game.getJoueur().getInventory().removeItem(weapon);
			game.getJoueur().setRankedWeapon(weapon);
			game.getJoueur().setActiveWeapon(weapon);

		} else if (weapon instanceof MeleeWeapon) {
			old = game.getJoueur().getMeleeWeapon();
			game.getJoueur().getInventory().removeItem(weapon);
			game.getJoueur().setMeleeWeapon(weapon);
			game.getJoueur().setActiveWeapon(weapon);
		}
		weapon.setUser(game.getJoueur());
		if (old != null && !(old instanceof NoWeapon)) {
			game.getJoueur().getInventory().addItem(old);
		}

	}

	@Override
	public String getAction() {
		return "équiper";
	}
}
