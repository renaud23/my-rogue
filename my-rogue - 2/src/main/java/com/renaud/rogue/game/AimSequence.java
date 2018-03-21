package com.renaud.rogue.game;

import com.renaud.rogue.event.KeyboardEvent;

public class AimSequence implements RogueSequence, KeyboardEvent {

	private Game game;

	public AimSequence(Game game) {
		this.game = game;
	}

	@Override
	public void activate() {

	}

	@Override
	public void keyUpPressed() {
		game.getJoueur().aimUp();
	}

	@Override
	public void keyDownPressed() {
		game.getJoueur().aimDown();
	}

	@Override
	public void keyLeftPressed() {
		game.getJoueur().aimLeft();
	}

	@Override
	public void keyRightPressed() {
		game.getJoueur().aimRight();
	}

}
