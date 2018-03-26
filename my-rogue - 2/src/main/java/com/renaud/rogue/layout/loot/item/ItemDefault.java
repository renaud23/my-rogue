package com.renaud.rogue.layout.loot.item;

import com.renaud.rogue.game.inventaire.Item;

public class ItemDefault implements ItemLayout {

    private Item item;

    public ItemDefault(Item item) {
	this.item = item;
    }

    @Override
    public Item getItem() {
	return item;
    }

    @Override
    public void weaponAction() {
	System.out.println("weaponAction");
    }

    @Override
    public void activateAction() {
	System.out.println("activateAction");
    }

}
