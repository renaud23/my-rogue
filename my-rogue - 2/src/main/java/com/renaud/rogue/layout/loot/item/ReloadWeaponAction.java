package com.renaud.rogue.layout.loot.item;

import com.renaud.rogue.game.sequence.Game;

public class ReloadWeaponAction implements ItemLayoutAction {

	private Game game;
	private boolean searchWeapon = false;
	private ItemLayout ammunation;

	public ReloadWeaponAction(Game game) {
		this.game = game;

	}

	@Override
	public void doIt(ItemLayout u, int i, int j) {
		if (!searchWeapon) {
			System.out.println("start");
			this.ammunation = u;
			ActionContext.getInstance().setWeaponAction(this);
			searchWeapon = true;
		} else {
			System.out.println("fini");
			ActionContext.getInstance().setWeaponAction(null);
		}

	}

}
