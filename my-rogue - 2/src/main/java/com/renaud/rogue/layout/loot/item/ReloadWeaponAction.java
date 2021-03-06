package com.renaud.rogue.layout.loot.item;

import com.renaud.rogue.game.inventaire.Ammunition;
import com.renaud.rogue.game.weapon.RankedWeapon;
import com.renaud.rogue.game.world.Game;
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
			GameConsoleDrawer.info("Choisissez une arme � recharger avec " + this.ammunation.getDesription());
		} else {
			if (u.getItem() instanceof RankedWeapon) {
				((RankedWeapon) u.getItem()).reload(this.ammunation);
				GameConsoleDrawer.inventory("Vous rechargez " + u.getItem().getDesription());
				if (this.ammunation.isEmpty()) {
					game.getJoueur().getInventory().removeItem(this.ammunation);
				}
			} else {
				GameConsoleDrawer.inventory("Vous ne pouvez pas rechargez " + u.getItem().getDesription() + " avec " + this.ammunation.getDesription());
			}

			ActionContext.getInstance().setWeaponAction(null);
			searchWeapon = false;
		}
	}

	@Override
	public String getAction() {
		return "recharger";
	}

}
