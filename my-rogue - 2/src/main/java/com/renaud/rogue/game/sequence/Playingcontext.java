package com.renaud.rogue.game.sequence;

public class Playingcontext {

	public final static int ACTION_MAX = 2;
	private int step;
	private int actions;
	private boolean playFinished;

	public void startGame() {
		step = 0;
		actions = ACTION_MAX;
		playFinished = false;
	}

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

	public int getStep() {
		return step;
	}
}
