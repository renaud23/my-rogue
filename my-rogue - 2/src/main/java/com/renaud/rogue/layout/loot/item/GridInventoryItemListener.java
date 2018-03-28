package com.renaud.rogue.layout.loot.item;

import com.renaud.rogue.layout.GridLayoutListener;

public class GridInventoryItemListener implements GridLayoutListener<ItemLayout> {

	@Override
	public void weaponAction(ItemLayout u, int i, int j) {
		if (u != null) {
			u.weaponAction(u, i, j);
		}
	}

	@Override
	public void activateAction(ItemLayout u, int i, int j) {
		if (u != null) {
			u.activateAction(u, i, j);
		}
	}

	@Override
	public void switchWeaponAction(ItemLayout u, int i, int j) {
		if (u != null) {
			u.switchWeaponAction(u, i, j);
		}
	}

	@Override
	public void inventaireAction(ItemLayout u, int i, int j) {
		if (u != null) {
			u.inventaireAction(u, i, j);
		}
	}

	@Override
	public void over(ItemLayout u, int i, int j) {
		if (u != null) {
			u.over(u, i, j);
		}
	}

	@Override
	public void annulerAction(ItemLayout u, int i, int j) {
		if (u != null) {
			u.annulerAction(u, i, j);
		}

	}

}
