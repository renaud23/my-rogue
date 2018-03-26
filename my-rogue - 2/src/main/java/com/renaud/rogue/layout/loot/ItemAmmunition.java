package com.renaud.rogue.layout.loot;

import com.renaud.rogue.game.inventaire.Item;

public class ItemAmmunition implements ItemInventory {

    private Item item;

    public ItemAmmunition(Item item) {
	this.item = item;
    }

    @Override
    public Item getItem() {
	return item;
    }

}
