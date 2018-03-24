package com.renaud.rogue.game.sequence;

public class ActivateAiming implements AimingAction {

    Game game;

    public ActivateAiming(Game game) {
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
    public void activate(Game game) {
	game.getJoueur().activate(game);
    }

}