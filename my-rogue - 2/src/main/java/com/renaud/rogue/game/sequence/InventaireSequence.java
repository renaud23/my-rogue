package com.renaud.rogue.game.sequence;

import com.renaud.rogue.game.event.ActionEvent;
import com.renaud.rogue.layout.loot.InventoryLayout;

public class InventaireSequence implements RogueSequence, ActionEvent {

	private Game game;

	private InventoryLayout layout;

	public InventaireSequence(Game game, InventoryLayout layout) {
		this.layout = layout;
		this.game = game;
	}

	@Override
	public void activate() {

	}

	@Override
	public void goUpAction() {
		layout.setChanged(true);
		layout.goUpAction();
	}

	@Override
	public void goDownAction() {
		layout.setChanged(true);
		layout.goDownAction();
	}

	@Override
	public void goLeftAction() {
		layout.setChanged(true);
		layout.goLeftAction();
	}

	@Override
	public void goRightAction() {
		layout.setChanged(true);
		layout.goRightAction();
	}

	@Override
	public void weaponAction() {
		layout.setChanged(true);
		layout.weaponAction();
	}

	@Override
	public void annulerAction() {
		layout.setChanged(true);
		if (layout.isOpened()) {
			layout.annulerAction();
		} else {
			// game.changeSequence(new PlayingSequence(game));
			SequenceAutomate.getInstance().setNextSequence(new PlayingSequence(game));
		}
	}

}
