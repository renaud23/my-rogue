package com.renaud.rogue.game.sequence;

import com.renaud.rogue.game.event.ActionEvent;
import com.renaud.rogue.game.world.Game;

public class AimingSequence implements RogueSequence, ActionEvent {

	private Game game;
	private AimingAction aiming;

	public AimingSequence(Game game, AimingAction aiming) {
		this.game = game;
		this.aiming = aiming;

	}

	@Override
	public void activate() {

	}

	@Override
	public void goUpAction() {
		aiming.aimUp();
	}

	@Override
	public void goDownAction() {
		aiming.aimDown();
	}

	@Override
	public void goLeftAction() {
		aiming.aimLeft();
	}

	@Override
	public void goRightAction() {
		aiming.aimRight();
	}

	@Override
	public void weaponAction() {
		this.aiming.activate(game);
		this.aiming.changeSequence(game);
	}

	public int getDepht() {
		return aiming.getDepht();
	}

	@Override
	public void annulerAction() {
		SequenceAutomate.getInstance().setNextSequence(new PlayingSequence(game));
	}

}
