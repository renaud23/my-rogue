package com.renaud.rogue.game;

import com.renaud.rogue.event.KeyboardEvent;

public class MainSequence implements RogueSequence, KeyboardEvent {

	private boolean onPlay;
	private boolean onInventaire;

	private Game game;
	private InventaireSequence inventaire;
	private RogueSequence currentSequence;

	public MainSequence(Game game, InventaireSequence inventaire) {
		this.game = game;
		this.inventaire = inventaire;
		this.onPlay = true;
		this.currentSequence = this.game;
	}

	@Override
	public void inventairePressed() {
		if (onPlay) {
			onPlay = false;
			onInventaire = true;
			currentSequence = inventaire;
		} else if (onInventaire) {
			onPlay = true;
			onInventaire = false;
			currentSequence = game;
		}
	}

	@Override
	public void activate() {
		currentSequence.activate();
	}

	@Override
	public void keyUpPressed() {
		currentSequence.keyUpPressed();
	}

	@Override
	public void keyDownPressed() {
		currentSequence.keyDownPressed();
	}

	@Override
	public void keyLeftPressed() {
		currentSequence.keyLeftPressed();
	}

	@Override
	public void keyRightPressed() {
		currentSequence.keyRightPressed();
	}

	@Override
	public void weaponPressed() {
		currentSequence.weaponPressed();
	}

	@Override
	public void switchWeaponPressed() {
		currentSequence.switchWeaponPressed();
	}

	@Override
	public void activatePressed() {
		currentSequence.activatePressed();
	}

	public boolean isOnPlay() {
		return onPlay;
	}

	public boolean isOnInventaire() {
		return onInventaire;
	}

}
