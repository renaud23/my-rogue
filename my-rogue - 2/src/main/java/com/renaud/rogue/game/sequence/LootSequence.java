package com.renaud.rogue.game.sequence;

import com.renaud.rogue.game.event.ActionEvent;

public class LootSequence implements RogueSequence, ActionEvent {

	private Game game;
	private int x;
	private int y;
	// private LootLayout layout;

	public LootSequence(Game game, int x, int y) {
		this.game = game;
		this.x = x;
		this.y = y;
		// this.layout = game.getLootLayout();
		// this.layout.initialise(game, game.getWorld().getTile(x, y));
		SequenceAutomate.getInstance().lootLayout.initialise(game, game.getWorld().getTile(x, y));
	}

	@Override
	public void goUpAction() {
		SequenceAutomate.getInstance().lootLayout.setChanged(true);
		SequenceAutomate.getInstance().lootLayout.goUpAction();
	}

	@Override
	public void goDownAction() {
		SequenceAutomate.getInstance().lootLayout.setChanged(true);
		SequenceAutomate.getInstance().lootLayout.goDownAction();
	}

	@Override
	public void goLeftAction() {
		SequenceAutomate.getInstance().lootLayout.setChanged(true);
		SequenceAutomate.getInstance().lootLayout.goLeftAction();
	}

	@Override
	public void goRightAction() {
		SequenceAutomate.getInstance().lootLayout.setChanged(true);
		SequenceAutomate.getInstance().lootLayout.goRightAction();
	}

	@Override
	public void weaponAction() {
		SequenceAutomate.getInstance().lootLayout.setChanged(true);
		SequenceAutomate.getInstance().lootLayout.weaponAction();
	}

	@Override
	public void annulerAction() {
		SequenceAutomate.getInstance().lootLayout.setChanged(true);
		if (SequenceAutomate.getInstance().lootLayout.isOpened()) {
			SequenceAutomate.getInstance().lootLayout.annulerAction();
		} else {
			// game.changeSequence(new PlayingSequence(game));
			SequenceAutomate.getInstance().setNextSequence(new PlayingSequence(game));
		}
	}

	@Override
	public void switchWeaponAction() {
		SequenceAutomate.getInstance().lootLayout.setChanged(true);
		SequenceAutomate.getInstance().lootLayout.switchWeaponAction();
	}

	@Override
	public void inventaireAction() {
		SequenceAutomate.getInstance().lootLayout.setChanged(true);
		SequenceAutomate.getInstance().lootLayout.inventaireAction();
	}

	@Override
	public void activateAction() {
		SequenceAutomate.getInstance().lootLayout.setChanged(true);
		SequenceAutomate.getInstance().lootLayout.activateAction();
	}

}
