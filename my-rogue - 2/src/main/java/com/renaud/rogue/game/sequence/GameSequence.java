package com.renaud.rogue.game.sequence;

import com.renaud.rogue.game.event.ActionEvent;
import com.renaud.rogue.layout.loot.InventoryLayout;
import com.renaud.rogue.layout.loot.LootLayout;

public class GameSequence implements RogueSequence, ActionEvent {

	public final static int ACTION_MAX = 2;
	private int step;
	private int actions = ACTION_MAX;
	private boolean playFinished;

	private RogueSequence currentSequence;

	private LootLayout lootLayout;
	private InventoryLayout inventoryLayout;

	public GameSequence(Game game, LootLayout lootLayout, InventoryLayout inventoryLayout) {
		this.lootLayout = lootLayout;
		this.inventoryLayout = inventoryLayout;

		this.currentSequence = new PlayingSequence(game);

	}

	public LootLayout getLootLayout() {
		return lootLayout;
	}

	/* activate sequence */

	@Override
	public void activate() {
		this.currentSequence.activate();
	}

	/* Action Event */

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
	public void activateAction() {
		this.currentSequence.activateAction();
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
	public void annulerAction() {
		this.currentSequence.annulerAction();
	}

	public void changeSequence(RogueSequence sequence) {
		this.currentSequence = sequence;
	}

	/* turn play */

	public void playFinished() {
		actions--;
		playFinished = true;
	}

	public boolean isPlayFinished() {
		return playFinished;
	}

	public boolean isTurnFinished() {
		return actions <= 0;
	}

	public void startNextTurn() {
		step++;
		actions = ACTION_MAX;
	}

	public void startNextPlay() {
		playFinished = false;
	}

}
