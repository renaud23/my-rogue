package com.renaud.rogue.game.sequence;

import com.renaud.rogue.game.world.Game;

public class ActivateAiming extends UseAiming {

	public ActivateAiming(Game game) {
		super(game);
	}

	@Override
	public void changeSequence(Game game) {
		SequenceAutomate.getInstance().setNextSequence(new PlayingSequence(game));
	}

	@Override
	public void activate(Game game) {
		game.getJoueur().activate(game);
	}

}