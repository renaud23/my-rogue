package com.renaud.ascii.world;

import com.renaud.ascii.event.OnEventAction;
import com.renaud.ascii.event.PlayerActionGestionnaire;
import com.renaud.ascii.monster.element.Monster;

public class MainLoop implements OnEventAction {

	private World world;

	private PlayerActionGestionnaire actionPlayer;

	private boolean playerStepFinished = false;

	public boolean activate() {
		// jeu au tour par tour
		if (playerStepFinished) {
			for (Monster m : world.getMonsters()) {
				m.activate(world);
			}
			playerStepFinished = false;
		} else {
			if (actionPlayer.activate()) {
				playerStepFinished = true;
			}
		}
		return false;
	}

	public MainLoop(World world, PlayerActionGestionnaire actionPlayer) {
		this.world = world;
		this.actionPlayer = actionPlayer;
	}

	@Override
	public void keyUpPressed() {
		actionPlayer.keyUpPressed();
	}

	@Override
	public void keyRightPressed() {
		actionPlayer.keyRightPressed();
	}

	@Override
	public void keyDownPressed() {
		actionPlayer.keyDownPressed();
	}

	@Override
	public void keyLeftPressed() {
		actionPlayer.keyLeftPressed();
	}
	/* */

	@Override
	public void keyUpReleased() {
		actionPlayer.keyUpReleased();
	}

	@Override
	public void keyDownReleased() {
		actionPlayer.keyDownReleased();
	}

	@Override
	public void keyLeftReleaseded() {
		actionPlayer.keyLeftReleaseded();
	}

	@Override
	public void keyRightRealesed() {
		actionPlayer.keyRightRealesed();
	}

	@Override
	public void spacePressed() {
		actionPlayer.spacePressed();

	}

	@Override
	public void spaceReleaseded() {
		actionPlayer.spaceReleaseded();

	}

	@Override
	public void mouseMoved(int x, int y, int varx, int vary) {
		// TODO Auto-generated method stub

	}
}
