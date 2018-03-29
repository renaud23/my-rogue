package com.renaud.rogue.game.sequence;

import com.renaud.rogue.game.event.ActionEvent;

public class MainSequence implements RogueSequence, ActionEvent {

	private Game game;
	private RogueSequence currentSequence;

	public MainSequence(Game game) {
		this.game = game;

		this.currentSequence = this.game;
	}

	@Override
	public void inventaireAction() {
		this.currentSequence.inventaireAction();
	}

	@Override
	public void activate() {
		currentSequence.activate();
	}

	@Override
	public void goUpAction() {
		currentSequence.goUpAction();
	}

	@Override
	public void goDownAction() {
		currentSequence.goDownAction();
	}

	@Override
	public void goLeftAction() {
		currentSequence.goLeftAction();
	}

	@Override
	public void goRightAction() {
		currentSequence.goRightAction();
	}

	@Override
	public void weaponAction() {
		currentSequence.weaponAction();
	}

	@Override
	public void switchWeaponAction() {
		currentSequence.switchWeaponAction();
	}

	@Override
	public void activateAction() {
		currentSequence.activateAction();
	}

	@Override
	public void annulerAction() {
		currentSequence.annulerAction();
	}

}
