package com.renaud.rogue.game;

import com.renaud.rogue.element.Joueur;
import com.renaud.rogue.event.KeyboardEvent;
import com.renaud.rogue.world.World;

public class MainSequence implements RogueSequence, KeyboardEvent {

	private Game game;
	private RogueSequence currentSequence;

	public MainSequence(World world, Joueur joueur) {
		this.game = new Game(world, joueur);
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
	public void spacePressed() {
		currentSequence.spacePressed();
	}

}
