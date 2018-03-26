package com.renaud.rogue.layout.loot.item;

import com.renaud.rogue.game.inventaire.Item;

public class ItemLayoutWeapon implements ItemLayout {

    private Item item;
    private ItemLayoutAction weaponAction;
    private ItemLayoutAction equipeWeapon;

    public ItemLayoutWeapon(Item item, ItemLayoutAction weaponAction) {
	this.item = item;
	this.weaponAction = weaponAction;
    }

    public ItemLayoutWeapon(Item item, ItemLayoutAction weaponAction, ItemLayoutAction equipeWeapon) {
	this.item = item;
	this.weaponAction = weaponAction;
	this.equipeWeapon = equipeWeapon;
    }

    @Override
    public Item getItem() {
	return item;
    }

    @Override
    public void weaponAction() {
	weaponAction.doIt();
    }

    @Override
    public void activateAction() {
	if (equipeWeapon != null) {
	    equipeWeapon.doIt();
	}
    }

}
