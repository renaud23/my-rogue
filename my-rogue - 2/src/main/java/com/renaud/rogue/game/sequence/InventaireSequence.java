package com.renaud.rogue.game.sequence;

import com.renaud.rogue.game.event.ActionEvent;

public class InventaireSequence implements RogueSequence, ActionEvent {

    private Game game;
    private ActionEvent layout;

    public InventaireSequence(Game game, ActionEvent layout) {
	this.game = game;
	this.layout = layout;
    }

    @Override
    public void activate() {

    }

    @Override
    public void goUpAction() {
	layout.goUpAction();
    }

    @Override
    public void goDownAction() {
	layout.goDownAction();
    }

    @Override
    public void goLeftAction() {
	layout.goLeftAction();
    }

    @Override
    public void goRightAction() {
	layout.goRightAction();
    }

    @Override
    public void weaponAction() {
	layout.weaponAction();
    }

    @Override
    public void annulerAction() {
	layout.annulerAction();
    }

}
