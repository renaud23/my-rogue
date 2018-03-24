package com.renaud.rogue.game.sequence;

import com.renaud.rogue.game.event.ActionEvent;

public class MainSequence implements RogueSequence, ActionEvent {

    private boolean onPlay;
    private boolean onInventaire;

    private Game game;
    private InventaireSequence inventaire;
    private RogueSequence currentSequence;

    public MainSequence(Game game, InventaireSequence inventaire) {
	this.game = game;
	this.inventaire = inventaire;
	this.onPlay = true;
	this.currentSequence = this.game;
    }

    @Override
    public void inventaireAction() {
	if (onPlay) {
	    onPlay = false;
	    onInventaire = true;
	    currentSequence = inventaire;
	} else if (onInventaire) {
	    onPlay = true;
	    onInventaire = false;
	    currentSequence = game;
	}
    }

    @Override
    public void activate() {
	currentSequence.activate();
    }

    @Override
    public void goUpAction() {
	currentSequence.goUpAction();
    }

    @Override
    public void goDownAction() {
	currentSequence.goDownAction();
    }

    @Override
    public void goLeftAction() {
	currentSequence.goLeftAction();
    }

    @Override
    public void goRightAction() {
	currentSequence.goRightAction();
    }

    @Override
    public void weaponAction() {
	currentSequence.weaponAction();
    }

    @Override
    public void switchWeaponAction() {
	currentSequence.switchWeaponAction();
    }

    @Override
    public void activateAction() {
	currentSequence.activateAction();
    }

    @Override
    public void annulerAction() {
	currentSequence.annulerAction();
    }

    public boolean isOnPlay() {
	return onPlay;
    }

    public boolean isOnInventaire() {
	return onInventaire;
    }

}
