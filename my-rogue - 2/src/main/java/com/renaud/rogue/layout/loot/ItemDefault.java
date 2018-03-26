package com.renaud.rogue.layout.loot;

import com.renaud.rogue.game.inventaire.Item;

public class ItemDefault implements ItemInventory {

    private Item item;

    public ItemDefault(Item item) {
	this.item = item;
    }

    @Override
    public Item getItem() {
	return item;
    }

}
