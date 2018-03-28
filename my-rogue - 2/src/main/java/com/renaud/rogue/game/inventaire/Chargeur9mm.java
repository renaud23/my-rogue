package com.renaud.rogue.game.inventaire;

import com.renaud.rogue.game.element.TileElement;

public class Chargeur9mm implements Item, Ammunition {

	private int ammunitions = 15;

	private TileElement tile = TileElement.Factory.createGunAmmo();

	public TileElement getTile() {
		return tile;
	}

	public int getAmmunitions() {
		return ammunitions;
	}

	public void setAmmunitions(int ammunitions) {
		this.ammunitions = ammunitions;
	}

	@Override
	public String getDesription() {
		return "Chargeur " + ammunitions + "x9mm";
	}

	@Override
	public int getType() {
		return Ammunition.BULLET_9MM;
	}

	@Override
	public int getAmmoLeft() {
		return ammunitions;
	}

	@Override
	public void removeAmmo(int nb) {
		ammunitions = Math.max(0, ammunitions - nb);
	}

	@Override
	public boolean isEmpty() {
		return ammunitions == 0;
	}

}
