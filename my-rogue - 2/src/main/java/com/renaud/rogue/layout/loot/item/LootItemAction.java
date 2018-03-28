package com.renaud.rogue.layout.loot.item;

import com.renaud.rogue.game.inventaire.Inventory;
import com.renaud.rogue.game.sequence.Game;
import com.renaud.rogue.game.world.TileDungeon;
import com.renaud.rogue.view.drawer.GameConsoleDrawer;

public class LootItemAction implements ItemLayoutAction {

	private Game game;
	private Inventory inventory;

	public LootItemAction(Game game) {
		this.game = game;
		this.inventory = game.getJoueur().getInventory();
	}

	@Override
	public void doIt(ItemLayout u, int i, int j) {
		TileDungeon tile = game.getWorld().getTile(game.getJoueur().getAimx(), game.getJoueur().getAimy());
		if (!inventory.isFull()) {
			tile.removeItem(u.getItem());
			inventory.addItem(u.getItem());
		} else {
			GameConsoleDrawer.carefull("Votre inventaire est plein.");
		}
	}

}
