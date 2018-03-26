package com.renaud.rogue.layout.loot.item;

import com.renaud.rogue.game.inventaire.Inventory;
import com.renaud.rogue.game.inventaire.Item;
import com.renaud.rogue.game.world.TileDungeon;
import com.renaud.rogue.view.drawer.GameConsoleDrawer;

public class LootItemAction implements ItemLayoutAction {

    private Inventory inventory;
    private TileDungeon tile;
    private Item item;

    public LootItemAction(Inventory inventory, TileDungeon tile, Item item) {
	this.inventory = inventory;
	this.tile = tile;
	this.item = item;
    }

    @Override
    public void doIt() {
	if (!inventory.isFull()) {
	    tile.removeItem(item);
	    inventory.addItem(item);
	} else {
	    GameConsoleDrawer.addLine("Votre inventaire est plein.", 0xFFFF00);
	}
    }

}
