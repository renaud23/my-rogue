package com.renaud.rogue.game.sequence;

import com.renaud.rogue.game.world.Game;

public class UseAiming implements AimingAction {

	Game game;

	public UseAiming(Game game) {
		this.game = game;
		this.game.getJoueur().resetAiming();
	}

	public void aimUp() {
		int e = Math.abs(this.game.getJoueur().getAimy() - 1 - this.game.getJoueur().getY());
		if (e <= 1) {
			this.game.getJoueur().setAimy(this.game.getJoueur().getAimy() - 1);
		}

	}

	public void aimDown() {
		int e = Math.abs(this.game.getJoueur().getAimy() + 1 - this.game.getJoueur().getY());
		if (e <= 1) {
			this.game.getJoueur().setAimy(this.game.getJoueur().getAimy() + 1);
		}
	}

	public void aimRight() {
		int e = Math.abs(this.game.getJoueur().getAimx() + 1 - this.game.getJoueur().getX());
		if (e <= 1) {
			this.game.getJoueur().setAimx(this.game.getJoueur().getAimx() + 1);
		}
	}

	public void aimLeft() {
		int e = Math.abs(this.game.getJoueur().getAimx() - 1 - this.game.getJoueur().getX());
		if (e <= 1) {
			this.game.getJoueur().setAimx(this.game.getJoueur().getAimx() - 1);
		}
	}

	@Override
	public int getDepht() {
		return 1;
	}

	@Override
	public void activate(Game game) {}

	@Override
	public void changeSequence(Game game) {
		// TileDungeon tile = game.getWorld().getTile(this.game.getJoueur().getAimx(), this.game.getJoueur().getAimy());
		// if (tile instanceof Activable) {
		// SequenceAutomate.getInstance().setNextSequence(new PlayingSequence(game));
		// } else {
		SequenceAutomate.getInstance().setNextSequence(new LootSequence(game, this.game.getJoueur().getAimx(), this.game.getJoueur().getAimy()));
		// }
	}

}