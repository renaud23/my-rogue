package com.renaud.rogue.layout.loot.item;

import com.renaud.rogue.game.sequence.Game;
import com.renaud.rogue.game.world.TileDungeon;
import com.renaud.rogue.view.drawer.GameConsoleDrawer;

public class ThrowItemAction implements ItemLayoutAction {

	private Game game;

	public ThrowItemAction(Game game) {
		this.game = game;
	}

	@Override
	public void doIt(ItemLayout u, int i, int j) {
		TileDungeon tile = game.getWorld().getTile(game.getJoueur().getAimx(), game.getJoueur().getAimy());

		tile.addItem(u.getItem());
		game.getJoueur().getInventory().removeItem(u.getItem());

		GameConsoleDrawer.inventory("Vous jeter " + u.getItem().getDesription());
	}

}