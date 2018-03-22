package com.renaud.rogue.game;

import com.renaud.rogue.element.Joueur;
import com.renaud.rogue.weapon.Weapon;

public class ShootingAiming implements AimingAction {

	Weapon weapon;
	Joueur joueur;

	public ShootingAiming(Joueur joueur, Weapon weapon) {
		this.weapon = weapon;
		this.joueur = joueur;
	}

	public void aimUp() {
		if (weapon.canAim(joueur, joueur.getAimx(), joueur.getAimy() - 1)) {
			joueur.setAimy(joueur.getAimy() - 1);
		}
	}

	public void aimDown() {
		if (weapon.canAim(joueur, joueur.getAimx(), joueur.getAimy() + 1)) {
			joueur.setAimy(joueur.getAimy() + 1);
		}
	}

	public void aimLeft() {
		if (weapon.canAim(joueur, joueur.getAimx() - 1, joueur.getAimy())) {
			joueur.setAimx(joueur.getAimx() - 1);
		}
	}

	public void aimRight() {
		if (weapon.canAim(joueur, joueur.getAimx() + 1, joueur.getAimy())) {
			joueur.setAimx(joueur.getAimx() + 1);
		}
	}

	@Override
	public int getDepht() {
		return weapon.getDepht();
	}

}