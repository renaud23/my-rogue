package com.renaud.rogue.layout.loot.item;

import com.renaud.rogue.game.inventaire.Ammunition;
import com.renaud.rogue.game.sequence.Game;
import com.renaud.rogue.game.weapon.RankedWeapon;
import com.renaud.rogue.view.drawer.GameConsoleDrawer;

public class ReloadWeaponAction implements ItemLayoutAction {

	private Game game;
	private boolean searchWeapon = false;
	private Ammunition ammunation;

	public ReloadWeaponAction(Game game) {
		this.game = game;

	}

	@Override
	public void doIt(ItemLayout u, int i, int j) {
		if (!searchWeapon) {
			this.ammunation = (Ammunition) u.getItem();
			ActionContext.getInstance().setWeaponAction(this);
			searchWeapon = true;
			GameConsoleDrawer.addLine("Choisissez une arme à recharger avec " + this.ammunation.getDesription(), 0xFFFF00);
		} else {
			if (u.getItem() instanceof RankedWeapon) {
				((RankedWeapon) u.getItem()).reload(this.ammunation);
				GameConsoleDrawer.addLine("Vous rechargez " + u.getItem().getDesription(), 0xFFFF00);
				if (this.ammunation.isEmpty()) {
					game.getJoueur().getInventory().removeItem(this.ammunation);
				}
			}
			ActionContext.getInstance().setWeaponAction(null);
			searchWeapon = false;
		}

	}

}
