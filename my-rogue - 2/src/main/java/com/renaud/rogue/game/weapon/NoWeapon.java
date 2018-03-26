package com.renaud.rogue.game.weapon;

import com.renaud.rogue.game.element.Joueur;
import com.renaud.rogue.game.element.Living;
import com.renaud.rogue.game.element.TileElement;
import com.renaud.rogue.game.sequence.Game;

public class NoWeapon implements Weapon {

    private Living user;
    private int damage = 0;

    public NoWeapon() {

    }

    @Override
    public int getDepht() {
	return 0;
    }

    public boolean canAim(Joueur joueur, int x, int y) {
	return false;
    }

    @Override
    public void shoot(Game game, int aimx, int aimy) {
    }

    public int getDamage() {
	return 0;
    }

    @Override
    public String getName() {
	return "no wepon";
    }

    public Living getUser() {
	return null;
    }

    @Override
    public TileElement getTile() {
	return null;
    }

    @Override
    public String getDesription() {
	return "Rien en main";
    }

    @Override
    public void setUser(Living user) {
	this.user = user;
    }
}
