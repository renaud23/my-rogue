package com.renaud.rogue.world;

import com.renaud.rogue.element.Element;

public class Tile {

    public final static long UNKNOW = 0l;
    public final static long WALL = 1l;
    public final static long FLOOR = 2l;

    public final static long PLAYER = 3l;
    public final static long WOLF = 4l;

    private long code;
    private char tile;
    private int color;
    private Element element;

    public Tile clone() {
	return new Tile(code, tile, color);
    }

    private Tile(long code, char tile, int color) {
	this.code = code;
	this.tile = tile;
	this.color = color;
    }

    public boolean canSeeThrought() {
	return this.code == FLOOR && (element == null ? true : !element.isOpaque());
    }

    public boolean canWalkOn() {
	return this.code == FLOOR && (element == null ? true : !element.isOpaque());
    }

    public boolean isEmpty() {
	return element == null;
    }

    public long getCode() {
	return code;
    }

    public char getTile() {
	return tile;
    }

    public int getColor() {
	return color;
    }

    public void setColor(int color) {
	this.color = color;
    }

    public Element getElement() {
	return element;
    }

    public void setElement(Element element) {
	this.element = element;
    }

    public static class Factory {

	public static Tile getUnknow() {
	    return new Tile(UNKNOW, '?', 0x505050);
	}

	public static Tile getWall() {
	    return new Tile(WALL, 'X', 0xAAAAAA);
	}

	public static Tile getFloor() {
	    return new Tile(FLOOR, '.', 0x009900);
	}

	public static Tile getPlayer() {
	    return new Tile(PLAYER, 'O', 0x505050);
	}

	public static Tile getWolf() {
	    return new Tile(WOLF, 'W', 0x663300);
	}
    }

}
