package com.renaud.rogue.game.sequence;

import com.renaud.rogue.game.event.ActionEvent;

public class AimSequence implements RogueSequence, ActionEvent {

	private Game game;

	public AimSequence(Game game) {
		this.game = game;
	}

	@Override
	public void activate() {

	}

	@Override
	public void goUpAction() {
		game.getJoueur().aimUp();
	}

	@Override
	public void goDownAction() {
		game.getJoueur().aimDown();
	}

	@Override
	public void goLeftAction() {
		game.getJoueur().aimLeft();
	}

	@Override
	public void goRightAction() {
		game.getJoueur().aimRight();
	}

}
