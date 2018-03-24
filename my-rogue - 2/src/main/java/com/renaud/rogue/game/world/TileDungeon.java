package com.renaud.rogue.game.world;

import java.util.ArrayList;
import java.util.List;

import com.renaud.rogue.game.element.Element;
import com.renaud.rogue.game.inventaire.Item;
import com.renaud.rogue.view.drawer.tile.RogueTile;

public class TileDungeon {

    public final static long UNKNOW = 0l;
    public final static long WALL = 1l;
    public final static long FLOOR = 2l;
    public final static long DOOR = 10002l;

    private long code;
    private char charCode;
    private int color;
    private Element occupant;
    private List<Element> objects = new ArrayList<>();
    private Light light = new Light(1.0f, 1.0f, 0.5f);
    private RogueTile tile;
    private List<Item> items = new ArrayList<>();

    public TileDungeon clone() {
	return new TileDungeon(code, charCode, color);
    }

    public TileDungeon(long code, char tile, int color) {
	this.code = code;
	this.charCode = tile;
	this.color = color;
    }

    protected TileDungeon(long code, char charCode, int color, RogueTile tile) {
	this.code = code;
	this.charCode = charCode;
	this.color = color;
	this.tile = tile;
    }

    public void addItem(Item item) {
	items.add(item);
    }

    public void removeItem(Item item) {
	items.remove(item);
    }

    public List<Item> getItem() {
	return items;
    }

    public boolean hasItem() {
	return !items.isEmpty();
    }

    public Item getLastItem() {
	return items.get(items.size() - 1);
    }

    public boolean isLighted() {
	return (light.pb + light.pr + light.pg) > 0f;
    }

    public boolean canSeeThrought() {
	if (this instanceof TileDoor) {
	    TileDoor door = (TileDoor) this;
	    return door.isOpen();
	}
	return this.code == FLOOR && (occupant == null ? true : !occupant.isOpaque());
    }

    public boolean canWalkOn() {
	if (this instanceof TileDoor) {
	    TileDoor door = (TileDoor) this;
	    return door.isOpen();
	}
	return this.code == FLOOR && occupant == null;
    }

    public boolean isEmpty() {
	return occupant == null;
    }

    public long getCode() {
	return code;
    }

    public char getCharCode() {
	return charCode;
    }

    public int getColor() {
	return color;
    }

    public void setColor(int color) {
	this.color = color;
    }

    public Element getOccupant() {
	return occupant;
    }

    public void setOccupant(Element element) {
	this.occupant = element;
    }

    public Light getLight() {
	return light;
    }

    public void setLight(Light light) {
	this.light = light;
    }

    public RogueTile getTile() {
	return tile;
    }

    public void addObject(Element object) {
	this.objects.add(object);
    }

    public void removeObject(Element object) {
	this.objects.remove(object);
    }

    public static class Factory {

	public static TileDungeon getUnknow() {
	    return new TileDungeon(UNKNOW, '?', 0x505050);
	}

	public static TileDungeon getWall() {
	    return new TileDungeon(WALL, 'X', 0x909090);
	}

	public static TileDungeon getFloor() {
	    return new TileDungeon(FLOOR, '.', 0x505050);
	}

	public static TileDungeon createDoor() {
	    return new TileDoor(DOOR, 'D', 0x505050);
	}

    }

}
