package com.renaud.rogue.layout.loot.item;

import com.renaud.rogue.layout.GridLayoutListener;

public class GridInventoryItemListener implements GridLayoutListener<ItemLayout> {

    @Override
    public void weaponAction(ItemLayout u, int i, int j) {
	if (u != null) {
	    u.weaponAction();
	}
    }

    @Override
    public void activateAction(ItemLayout u, int i, int j) {
	if (u != null) {
	    u.activateAction();
	}
    }

    @Override
    public void switchWeaponAction(ItemLayout u, int i, int j) {
	if (u != null) {
	    u.switchWeaponAction();
	}
    }

    @Override
    public void inventaireAction(ItemLayout u, int i, int j) {
	if (u != null) {
	    u.inventaireAction();
	}
    }

    @Override
    public void over(ItemLayout u, int i, int j) {
	if (u != null) {
	    u.over();
	}
    }

}
