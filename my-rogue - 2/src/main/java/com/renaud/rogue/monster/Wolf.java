package com.renaud.rogue.monster;

import com.renaud.rogue.comportement.Comportement;
import com.renaud.rogue.comportement.HuntPlayer;
import com.renaud.rogue.comportement.RandomWalk;
import com.renaud.rogue.game.Game;
import com.renaud.rogue.world.Tile;

public class Wolf implements Monster {

    int x;
    int y;
    int step = 2;
    int depth = 15;
    int life = 10;
    private boolean isHuting;

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

	if (isHuting) {
	    hunt.activate(game);
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
	return true;
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
    public int getStep() {
	return step;
    }

    @Override
    public boolean isDead() {
	return life <= 0;
    }

    @Override
    public void startTurn() {
	// TODO Auto-generated method stub

    }

    @Override
    public boolean turnIsEnd() {
	// TODO Auto-generated method stub
	return false;
    }
}
