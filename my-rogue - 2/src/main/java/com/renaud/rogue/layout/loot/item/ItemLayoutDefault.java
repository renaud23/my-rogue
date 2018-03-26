package com.renaud.rogue.layout.loot.item;

import com.renaud.rogue.game.inventaire.Item;
import com.renaud.rogue.view.drawer.GameConsoleDrawer;

public class ItemLayoutDefault implements ItemLayout {

    private Item item;
    private ItemLayoutAction activateAction;

    public ItemLayoutDefault(Item item, ItemLayoutAction weaponAction) {
	this.item = item;
	this.activateAction = weaponAction;
    }

    @Override
    public Item getItem() {
	return item;
    }

    @Override
    public void weaponAction() {
	this.activateAction.doIt();
    }

    @Override
    public void over() {
	GameConsoleDrawer.addLine(item.getDesription(), 0xFFFF00);
    }

}
