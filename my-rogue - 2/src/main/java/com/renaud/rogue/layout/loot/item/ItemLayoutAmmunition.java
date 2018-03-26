package com.renaud.rogue.layout.loot.item;

import com.renaud.rogue.game.inventaire.Item;

public class ItemLayoutAmmunition implements ItemLayout {

    private Item item;

    public ItemLayoutAmmunition(Item item) {
	this.item = item;
    }

    @Override
    public Item getItem() {
	return item;
    }

}
