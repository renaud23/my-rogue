package com.renaud.rogue.game.sequence;

import com.renaud.rogue.game.event.ActionEvent;

public class AimSequence implements RogueSequence, ActionEvent {

    private Game game;
    private AimingAction aiming;

    public AimSequence(Game game, AimingAction aiming) {
	this.game = game;
	this.aiming = aiming;
    }

    @Override
    public void activate() {

    }

    @Override
    public void goUpAction() {
	aiming.aimUp();
    }

    @Override
    public void goDownAction() {
	aiming.aimDown();
    }

    @Override
    public void goLeftAction() {
	aiming.aimLeft();
    }

    @Override
    public void goRightAction() {
	aiming.aimRight();
    }

    @Override
    public void weaponAction() {
	this.aiming.activate(game);
	game.changeSequence(new PlayingSequence(game));
    }

}
