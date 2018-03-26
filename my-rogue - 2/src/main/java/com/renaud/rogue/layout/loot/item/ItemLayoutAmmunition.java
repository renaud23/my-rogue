package com.renaud.rogue.layout.loot.item;

import com.renaud.rogue.game.inventaire.Item;
import com.renaud.rogue.view.drawer.GameConsoleDrawer;

public class ItemLayoutAmmunition implements ItemLayout {

    private Item item;
    private ItemLayout action;

    public ItemLayoutAmmunition(Item item, ItemLayout action) {
	this.item = item;
    }

    @Override
    public Item getItem() {
	return item;
    }

    @Override
    public void weaponAction() {

    }

    @Override
    public void over() {
	GameConsoleDrawer.addLine(item.getDesription(), 0x0000FF);
    }

}
