package com.renaud.rogue.game.sequence;

import com.renaud.rogue.game.element.Joueur;

public class ActivateAiming implements AimingAction {

    Joueur joueur;

    public ActivateAiming(Joueur joueur) {
	this.joueur = joueur;
    }

    public void aimUp() {
	int e = Math.abs(joueur.getAimy() - 1 - joueur.getY());
	if (e <= 1) {
	    joueur.setAimy(joueur.getAimy() - 1);
	}

    }

    public void aimDown() {
	int e = Math.abs(joueur.getAimy() + 1 - joueur.getY());
	if (e <= 1) {
	    joueur.setAimy(joueur.getAimy() + 1);
	}
    }

    public void aimRight() {
	int e = Math.abs(joueur.getAimx() + 1 - joueur.getX());
	if (e <= 1) {
	    joueur.setAimx(joueur.getAimx() + 1);
	}
    }

    public void aimLeft() {
	int e = Math.abs(joueur.getAimx() - 1 - joueur.getX());
	if (e <= 1) {
	    joueur.setAimx(joueur.getAimx() - 1);
	}
    }

    @Override
    public int getDepht() {
	return 1;
    }

}