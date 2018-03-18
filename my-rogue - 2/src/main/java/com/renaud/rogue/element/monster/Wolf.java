package com.renaud.rogue.element.monster;

import com.renaud.rogue.element.Monster;
import com.renaud.rogue.element.comportement.Comportement;
import com.renaud.rogue.element.comportement.HuntPlayer;
import com.renaud.rogue.element.comportement.RandomWalk;
import com.renaud.rogue.game.Game;
import com.renaud.rogue.world.Tile;

public class Wolf implements Monster {

    int x;
    int y;
    int actions = 3;
    int actionsMax = 3;
    int depth = 10;
    int life = 10;
    private boolean isHuting = false;

    private Comportement walk;
    private Comportement hunt;

    public Wolf(int x, int y) {
	this.x = x;
	this.y = y;
	walk = new RandomWalk(this);
	hunt = new HuntPlayer(this);
    }

    @Override
    public void activate(Game game) {
	actions--;
	if (Math.abs(this.x - game.getJoueur().getX()) <= 1 && Math.abs(this.y - game.getJoueur().getY()) <= 1) {
	    // System.out.println("byte");
	} else if (isHuting) {
	    hunt.activate(game);
	    if (hunt.isFinished()) {
		isHuting = false;
	    }
	} else {
	    if (game.getWorld().canSee(this, game.getJoueur())) {
		isHuting = true;
		hunt.reset();
		activate(game);
	    } else {
		walk.activate(game);
	    }
	}
    }

    @Override
    public int getX() {
	return x;
    }

    @Override
    public int getY() {
	return y;
    }

    @Override
    public void addX(int dx) {
	x += dx;
    }

    @Override
    public void addY(int dy) {
	y += dy;
    }

    @Override
    public boolean isOpaque() {
	return false;
    }

    @Override
    public int getDepht() {
	return depth;
    }

    @Override
    public Tile getTile() {
	return Tile.Factory.getWolf();
    }

    @Override
    public boolean isDead() {
	return life <= 0;
    }

    @Override
    public void startTurn() {
	actions = actionsMax;
    }

    @Override
    public boolean turnIsEnd() {
	return actions <= 0 || isDead();
    }

    public void setX(int x) {
	this.x = x;
    }

    public void setY(int y) {
	this.y = y;
    }

}
