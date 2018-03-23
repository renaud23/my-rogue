package com.renaud.rogue.sequence;

import com.renaud.rogue.event.KeyboardEvent;

public class InventaireSequence implements RogueSequence, KeyboardEvent {

	private Game game;
	private KeyboardEvent layout;

	public InventaireSequence(Game game, KeyboardEvent layout) {
		this.game = game;
		this.layout = layout;
	}

	@Override
	public void activate() {

	}

	@Override
	public void keyUpPressed() {
		layout.keyUpPressed();
	}

	@Override
	public void keyDownPressed() {
		layout.keyDownPressed();
	}

	@Override
	public void keyLeftPressed() {
		layout.keyLeftPressed();
	}

	@Override
	public void keyRightPressed() {
		layout.keyRightPressed();
	}

	@Override
	public void weaponPressed() {
		layout.weaponPressed();
	}

}
