package com.renaud.rogue.game.sequence;

import com.renaud.rogue.game.event.ActionEvent;

public class InventorySequence implements RogueSequence, ActionEvent {

	private Game game;

	public InventorySequence(Game game) {
		this.game = game;
	}

	@Override
	public void activate() {}

	@Override
	public void goUpAction() {
		SequenceAutomate.getInstance().getInventoryLayout().setChanged(true);
		SequenceAutomate.getInstance().getInventoryLayout().goUpAction();
	}

	@Override
	public void goDownAction() {
		SequenceAutomate.getInstance().getInventoryLayout().setChanged(true);
		SequenceAutomate.getInstance().getInventoryLayout().goDownAction();
	}

	@Override
	public void goLeftAction() {
		SequenceAutomate.getInstance().getInventoryLayout().setChanged(true);
		SequenceAutomate.getInstance().getInventoryLayout().goLeftAction();
	}

	@Override
	public void goRightAction() {
		SequenceAutomate.getInstance().getInventoryLayout().setChanged(true);
		SequenceAutomate.getInstance().getInventoryLayout().goRightAction();
	}

	@Override
	public void weaponAction() {
		SequenceAutomate.getInstance().getInventoryLayout().setChanged(true);
		SequenceAutomate.getInstance().getInventoryLayout().weaponAction();
	}

	@Override
	public void annulerAction() {
		SequenceAutomate.getInstance().getInventoryLayout().setChanged(true);
		if (SequenceAutomate.getInstance().getInventoryLayout().isOpened()) {
			SequenceAutomate.getInstance().getInventoryLayout().annulerAction();
		} else {
			SequenceAutomate.getInstance().setNextSequence(new PlayingSequence(game));
		}
	}

}
