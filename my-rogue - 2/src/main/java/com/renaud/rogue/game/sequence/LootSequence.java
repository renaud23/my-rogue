package com.renaud.rogue.game.sequence;

import com.renaud.rogue.game.element.Joueur;
import com.renaud.rogue.game.event.ActionEvent;

public class LootSequence implements RogueSequence, ActionEvent {

    private Joueur joueur;
    private ActionEvent layout;

    public LootSequence(Joueur joueur, ActionEvent layout) {
	this.joueur = joueur;
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
