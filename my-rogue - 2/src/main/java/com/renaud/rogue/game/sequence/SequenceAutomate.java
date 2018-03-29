package com.renaud.rogue.game.sequence;

import com.renaud.rogue.layout.loot.InventoryLayout;
import com.renaud.rogue.layout.loot.LootLayout;

public class SequenceAutomate implements RogueSequence {

	public LootLayout lootLayout;
	public InventoryLayout inventoryLayout;

	private Playingcontext playingContext;

	public Playingcontext getPlayingContext() {
		return playingContext;
	}

	private RogueSequence currentSequence;

	public static SequenceAutomate instance;

	private SequenceAutomate() {
		this.playingContext = new Playingcontext();
		this.playingContext.startGame();
	}

	public static SequenceAutomate getInstance() {
		if (instance == null) {
			instance = new SequenceAutomate();
		}
		return instance;
	}

	public void setNextSequence(RogueSequence sequence) {
		this.currentSequence = sequence;
	}

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

	public boolean isOnLoot() {
		return currentSequence instanceof LootSequence;
	}

	public boolean isOnAiming() {
		return currentSequence instanceof AimSequence;
	}

	public int getAimingDepht() {
		if (isOnAiming()) {
			return ((AimSequence) currentSequence).getDepht();
		}
		return 0;
	}

}
