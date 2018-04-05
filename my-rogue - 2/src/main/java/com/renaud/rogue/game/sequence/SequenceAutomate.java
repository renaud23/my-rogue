package com.renaud.rogue.game.sequence;

import com.renaud.rogue.layout.loot.InventoryLayout;
import com.renaud.rogue.layout.loot.LootLayout;

public class SequenceAutomate implements RogueSequence {

	private LootLayout lootLayout;
	private InventoryLayout inventoryLayout;
	private Playingcontext playingContext;
	private RogueSequence currentSequence;

	public static SequenceAutomate instance;

	private SequenceAutomate() {
		this.playingContext = new Playingcontext();
		this.playingContext.startGame();
	}

	public Playingcontext getPlayingContext() {
		return playingContext;
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
	public void useAction() {
		this.currentSequence.useAction();
	}

	@Override
	public void annulerAction() {
		this.currentSequence.annulerAction();
	}

	@Override
	public void activate() {
		this.currentSequence.activate();
	}

	@Override
	public void activateAction() {
		this.currentSequence.activateAction();
	}

	/* */

	public boolean isOnLoot() {
		return currentSequence instanceof LootSequence;
	}

	public boolean isOnAiming() {
		return currentSequence instanceof AimingSequence;
	}

	public int getAimingDepht() {
		if (isOnAiming()) {
			return ((AimingSequence) currentSequence).getDepht();
		}
		return 0;
	}

	/* */

	public LootLayout getLootLayout() {
		return lootLayout;
	}

	public void setLootLayout(LootLayout lootLayout) {
		this.lootLayout = lootLayout;
	}

	public InventoryLayout getInventoryLayout() {
		return inventoryLayout;
	}

	public void setInventoryLayout(InventoryLayout inventoryLayout) {
		this.inventoryLayout = inventoryLayout;
	}

}
