package com.renaud.rogue.game.sequence;

import com.renaud.rogue.game.event.ActionEvent;
import com.renaud.rogue.game.layout.LootLayout;

public class LootSequence implements RogueSequence, ActionEvent {

    private Game game;
    private int x;
    private int y;
    private LootLayout layout;

    public LootSequence(Game game, int x, int y) {
	this.game = game;
	this.x = x;
	this.y = y;
	this.layout = game.getLootLayout();
    }

    @Override
    public void activate() {

    }

    @Override
    public void goUpAction() {
	layout.setChanged(true);
	layout.goUpAction();
    }

    @Override
    public void goDownAction() {
	layout.setChanged(true);
	layout.goDownAction();
    }

    @Override
    public void goLeftAction() {
	layout.setChanged(true);
	layout.goLeftAction();
    }

    @Override
    public void goRightAction() {
	layout.setChanged(true);
	layout.goRightAction();
    }

    @Override
    public void weaponAction() {
	layout.setChanged(true);
	layout.weaponAction();
    }

    @Override
    public void annulerAction() {
	layout.setChanged(true);
	if (layout.isOpened()) {
	    layout.annulerAction();
	} else {
	    game.changeSequence(new PlayingSequence(game));
	}
    }

}
