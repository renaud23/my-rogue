package com.renaud.rogue.game.sequence;

import com.renaud.rogue.game.element.Joueur;
import com.renaud.rogue.game.weapon.Weapon;
import com.renaud.rogue.game.world.Game;

public class ShootingAiming implements AimingAction {

	Weapon weapon;
	Joueur joueur;

	public ShootingAiming(Joueur joueur) {
		this.weapon = joueur.getActiveWeapon();
		this.joueur = joueur;
		this.joueur.resetAiming();
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

	@Override
	public void activate(Game game) {
		this.joueur.shoot(game);
	}

	@Override
	public void changeSequence(Game game) {
		SequenceAutomate.getInstance().setNextSequence(new PlayingSequence(game));
	}

}