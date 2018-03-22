package com.renaud.rogue.game;

import com.renaud.rogue.event.KeyboardEvent;

public class InventaireSequence implements RogueSequence, KeyboardEvent {

	private Game game;

	public InventaireSequence(Game game) {
		this.game = game;
	}

	@Override
	public void activate() {

	}

}
