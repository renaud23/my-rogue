package com.renaud.rogue.layout.loot.item;

import com.renaud.rogue.game.sequence.Game;

public class EquipeWeaponAction implements ItemLayoutAction {

	private Game game;

	public EquipeWeaponAction(Game game) {
		this.game = game;
	}

	@Override
	public void doIt(ItemLayout u, int i, int j) {
		System.out.println("bhouu");

	}

}
