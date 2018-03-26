package com.renaud.rogue.game.weapon;

import com.renaud.rogue.game.element.Joueur;
import com.renaud.rogue.game.element.Living;
import com.renaud.rogue.game.element.TileElement;
import com.renaud.rogue.game.sequence.Game;
import com.renaud.rogue.game.world.TileDungeon;

public class Knife implements MeleeWeapon {

    private Living user;
    private int damage = 5;
    private TileElement tile = TileElement.Factory.createKnife();

    public Knife(Living user) {
	this.user = user;
    }

    @Override
    public int getDepht() {
	return 1;
    }

    public boolean canAim(Joueur joueur, int x, int y) {
	int dx = Math.abs(x - joueur.getX());
	int dy = Math.abs(y - joueur.getY());
	if (dx <= 1 && dy <= 1)
	    return true;
	return false;
    }

    @Override
    public void shoot(Game game, int aimx, int aimy) {
	TileDungeon tile = game.getWorld().getTile(aimx, aimy);
	if (!tile.isEmpty()) {
	    if (tile.getOccupant() instanceof Joueur) {
		System.out.println("vous vous infligé un sépuku rituel !");
	    } else if (tile.getOccupant() instanceof Living) {
		((Living) tile.getOccupant()).injured(game, this);
	    }
	}
    }

    public int getDamage() {
	return this.damage * this.user.getLevel();
    }

    @Override
    public String getName() {
	return "knife";
    }

    public Living getUser() {
	return user;
    }

    @Override
    public TileElement getTile() {
	return tile;
    }

    @Override
    public String getDesription() {
	return "Couteau de cuisine";
    }

    @Override
    public void setUser(Living user) {
	this.user = user;
    }

}
