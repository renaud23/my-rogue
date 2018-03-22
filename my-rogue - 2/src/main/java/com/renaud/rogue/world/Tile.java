package com.renaud.rogue.world;

import java.util.ArrayList;
import java.util.List;

import com.renaud.rogue.drawer.sprite.TorcheSprite;
import com.renaud.rogue.drawer.tile.FireballTile;
import com.renaud.rogue.drawer.tile.GhoulTile;
import com.renaud.rogue.drawer.tile.HeroTile;
import com.renaud.rogue.drawer.tile.RogueTile;
import com.renaud.rogue.drawer.tile.WolfTile;
import com.renaud.rogue.element.Element;

public class Tile {

	public final static long UNKNOW = 0l;
	public final static long WALL = 1l;
	public final static long FLOOR = 2l;

	public final static long PLAYER = 101l;
	public final static long WOLF = 102l;
	public final static long GHOUL = 103l;

	public final static long FIREBALL = 1001l;

	public final static long TORCHE = 10001l;
	public final static long DOOR = 10002l;

	private long code;
	private char charCode;
	private int color;
	private Element occupant;
	private List<Element> objects = new ArrayList<>();
	private Light light = new Light(1.0f, 1.0f, 0.5f);
	private RogueTile tile;

	public Tile clone() {
		return new Tile(code, charCode, color);
	}

	protected Tile(long code, char tile, int color) {
		this.code = code;
		this.charCode = tile;
		this.color = color;
	}

	protected Tile(long code, char charCode, int color, RogueTile tile) {
		this.code = code;
		this.charCode = charCode;
		this.color = color;
		this.tile = tile;
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

		public static Tile getUnknow() {
			return new Tile(UNKNOW, '?', 0x505050);
		}

		public static Tile getWall() {
			return new Tile(WALL, 'X', 0x909090);
		}

		public static Tile getFloor() {
			return new Tile(FLOOR, '.', 0x505050);
		}

		public static Tile getPlayer() {
			return new Tile(PLAYER, '@', 0xEEEE00, new HeroTile());
		}

		public static Tile getWolf() {
			return new Tile(WOLF, 'W', 0xAA0000, new WolfTile());
		}

		public static Tile getGhoul() {
			return new Tile(GHOUL, 'G', 0x0000AA, new GhoulTile());
		}

		public static Tile getFireball() {
			return new Tile(FIREBALL, '*', 0xAAAA00, new FireballTile());
		}

		public static Tile getTorche() {
			return new Tile(TORCHE, 'i', 0xEE0000, new TorcheSprite());
		}

		public static Tile createDoor() {
			return new TileDoor(DOOR, 'D', 0xEE2050);
		}

	}

}
