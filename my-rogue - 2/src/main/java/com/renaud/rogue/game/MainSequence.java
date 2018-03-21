package com.renaud.rogue.game;

import com.renaud.rogue.event.KeyboardEvent;

public class MainSequence implements RogueSequence, KeyboardEvent {

	private Game game;
	private RogueSequence currentSequence;

	public MainSequence(Game game) {
		this.game = game;
		this.currentSequence = this.game;
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
	public void rankedWeaponPressed() {
		currentSequence.rankedWeaponPressed();
	}

	@Override
	public void switchWeaponPressed() {
		currentSequence.switchWeaponPressed();
	}

}
