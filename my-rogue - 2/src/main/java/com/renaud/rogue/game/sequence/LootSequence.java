package com.renaud.rogue.game.sequence;

import com.renaud.rogue.game.event.ActionEvent;

public class LootSequence implements RogueSequence, ActionEvent {

	private Game game;

	public LootSequence(Game game, int x, int y) {
		this.game = game;
		SequenceAutomate.getInstance().getLootLayout().initialise(game, game.getWorld().getTile(x, y));
	}

	@Override
	public void goUpAction() {
		SequenceAutomate.getInstance().getLootLayout().setChanged(true);
		SequenceAutomate.getInstance().getLootLayout().goUpAction();
	}

	@Override
	public void goDownAction() {
		SequenceAutomate.getInstance().getLootLayout().setChanged(true);
		SequenceAutomate.getInstance().getLootLayout().goDownAction();
	}

	@Override
	public void goLeftAction() {
		SequenceAutomate.getInstance().getLootLayout().setChanged(true);
		SequenceAutomate.getInstance().getLootLayout().goLeftAction();
	}

	@Override
	public void goRightAction() {
		SequenceAutomate.getInstance().getLootLayout().setChanged(true);
		SequenceAutomate.getInstance().getLootLayout().goRightAction();
	}

	@Override
	public void weaponAction() {
		SequenceAutomate.getInstance().getLootLayout().setChanged(true);
		SequenceAutomate.getInstance().getLootLayout().weaponAction();
	}

	@Override
	public void annulerAction() {
		SequenceAutomate.getInstance().getLootLayout().setChanged(true);
		if (SequenceAutomate.getInstance().getLootLayout().isOpened()) {
			SequenceAutomate.getInstance().getLootLayout().annulerAction();
		} else {
			SequenceAutomate.getInstance().setNextSequence(new PlayingSequence(game));
		}
	}

	@Override
	public void switchWeaponAction() {
		SequenceAutomate.getInstance().getLootLayout().setChanged(true);
		SequenceAutomate.getInstance().getLootLayout().switchWeaponAction();
	}

	@Override
	public void inventaireAction() {
		SequenceAutomate.getInstance().getLootLayout().setChanged(true);
		SequenceAutomate.getInstance().getLootLayout().inventaireAction();
	}

	@Override
	public void useAction() {
		SequenceAutomate.getInstance().getLootLayout().setChanged(true);
		SequenceAutomate.getInstance().getLootLayout().useAction();
	}

}
