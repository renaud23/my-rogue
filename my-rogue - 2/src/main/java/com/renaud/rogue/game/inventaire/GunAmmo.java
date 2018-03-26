package com.renaud.rogue.game.inventaire;

import com.renaud.rogue.game.element.TileElement;

public class GunAmmo implements Item, Ammunition {

    private int ammunitions = 12;

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
	return "Des munitions pour le gun gun gun !";
    }

}
