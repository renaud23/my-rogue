package com.renaud.rogue.layout.loot.item;

import com.renaud.rogue.game.sequence.Game;

public class ReloadWeaponAction implements ItemLayoutAction {

	private Game game;
	private ActionContext context;
	private boolean searchWeapon = false;

	public ReloadWeaponAction(ActionContext context, Game game) {
		this.game = game;
		this.context = context;
	}

	@Override
	public void doIt(ItemLayout u, int i, int j) {
		if (!searchWeapon) {
			System.out.println("start");
			this.context.setWeaponAction(this);
			searchWeapon = true;
		} else {
			System.out.println("fini");
			this.context.setWeaponAction(null);
		}

	}

}
