package com.renaud.rogue.game.sequence;

import com.renaud.rogue.game.element.Joueur;
import com.renaud.rogue.game.event.ActionEvent;
import com.renaud.rogue.layout.loot.InventoryLayout;

public class InventaireSequence implements RogueSequence, ActionEvent {

    private Joueur joueur;
    private InventoryLayout layout;

    public InventaireSequence(Joueur joueur, InventoryLayout layout) {
	this.layout = layout;
	this.joueur = joueur;
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
	layout.annulerAction();
    }

}
