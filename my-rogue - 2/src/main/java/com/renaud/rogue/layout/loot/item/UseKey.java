package com.renaud.rogue.layout.loot.item;

import com.renaud.rogue.game.inventaire.KeyDoor;
import com.renaud.rogue.game.sequence.Game;
import com.renaud.rogue.game.world.TileDoor;
import com.renaud.rogue.game.world.TileDungeon;
import com.renaud.rogue.view.drawer.GameConsoleDrawer;

public class UseKey implements ItemLayoutAction {

	private Game game;

	public UseKey(Game game) {
		this.game = game;
	}

	@Override
	public void doIt(ItemLayout u, int i, int j) {
		TileDungeon tile = game.getWorld().getTile(game.getJoueur().getAimx(), game.getJoueur().getAimy());

		if (tile instanceof TileDoor) {
			TileDoor door = (TileDoor) tile;
			if (!door.isLocked()) {
				GameConsoleDrawer.info("Cette porte n'est pas fermée.");
			} else {
				if (door.unlock((KeyDoor) u.getItem())) {
					GameConsoleDrawer.info("La clef tourne dans la serrure. vous pouver ouvirr la porte.");
				} else {
					GameConsoleDrawer.info("Cette clef n'ouvre pas cette porte.");
				}
			}
		} else {
			GameConsoleDrawer.info("Ce n'est pas une porte.");
		}
	}

	@Override
	public String getAction() {
		return "utiliser";
	}
}
