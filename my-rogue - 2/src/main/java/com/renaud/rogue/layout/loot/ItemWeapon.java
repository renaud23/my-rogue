package com.renaud.rogue.layout.loot;

import com.renaud.rogue.game.inventaire.Item;

public class ItemWeapon implements ItemInventory {

    private Item item;

    public ItemWeapon(Item item) {
	this.item = item;
    }

    @Override
    public Item getItem() {
	return item;
    }

}
