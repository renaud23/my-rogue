package com.renaud.rogue.game.sequence;

public class SequenceAutomate implements RogueSequence {

	private RogueSequence currentSequence;

	public static SequenceAutomate instance;

	private SequenceAutomate() {}

	public static SequenceAutomate getInstance() {
		if (instance == null) {
			instance = new SequenceAutomate();
		}
		return instance;
	}

	public void setNextSequence(RogueSequence sequence) {
		this.currentSequence = sequence;
	}

	// public RogueSequence getCurrentSequence() {
	// return currentSequence;
	// }

	@Override
	public void goUpAction() {
		this.currentSequence.goUpAction();
	}

	@Override
	public void goDownAction() {
		this.currentSequence.goDownAction();
	}

	@Override
	public void goLeftAction() {
		this.currentSequence.goLeftAction();
	}

	@Override
	public void goRightAction() {
		this.currentSequence.goRightAction();
	}

	@Override
	public void weaponAction() {
		this.currentSequence.weaponAction();
	}

	@Override
	public void switchWeaponAction() {
		this.currentSequence.switchWeaponAction();
	}

	@Override
	public void inventaireAction() {
		this.currentSequence.inventaireAction();
	}

	@Override
	public void activateAction() {
		this.currentSequence.activateAction();
	}

	@Override
	public void annulerAction() {
		this.currentSequence.annulerAction();
	}

	@Override
	public void activate() {
		this.currentSequence.activate();
	}

}
