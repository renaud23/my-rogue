package com.renaud.rogue.element;

import com.renaud.rogue.world.Tile;

public class Blank implements Element {

    private int x;
    private int y;

    public Blank(int x, int y) {
	this.x = x;
	this.y = y;
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
    public Tile getTile() {
	return Tile.Factory.getUnknow();
    }

    @Override
    public boolean isOpaque() {
	return false;
    }

}
