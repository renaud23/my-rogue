package com.renaud.rogue.world;

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

	private long code;
	private char tile;
	private int color;
	private Element element;
	private Light light = new Light(1.0f, 1.0f, 0.5f);

	public Tile clone() {
		return new Tile(code, tile, color);
	}

	private Tile(long code, char tile, int color) {
		this.code = code;
		this.tile = tile;
		this.color = color;
	}

	public boolean isLighted() {
		return (light.pb + light.pr + light.pg) > 0f;
	}

	public boolean canSeeThrought() {
		return this.code == FLOOR && (element == null ? true : !element.isOpaque());
	}

	public boolean canWalkOn() {
		return this.code == FLOOR && element == null;
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

	public Light getLight() {
		return light;
	}

	public void setLight(Light light) {
		this.light = light;
	}

	public static class Factory {

		public static Tile getUnknow() {
			return new Tile(UNKNOW, '?', 0x505050);
		}

		public static Tile getWall() {
			return new Tile(WALL, 'X', 0x909090);
		}

		public static Tile getFloor() {
			return new Tile(FLOOR, '.', 0x507050);
		}

		public static Tile getPlayer() {
			return new Tile(PLAYER, '@', 0xEEEE00);
		}

		public static Tile getWolf() {
			return new Tile(WOLF, 'W', 0xAA0000);
		}

		public static Tile getGhoul() {
			return new Tile(GHOUL, 'G', 0x0000AA);
		}

		public static Tile getFireball() {
			return new Tile(FIREBALL, '*', 0xAAAA00);
		}

		public static Tile getTorche() {
			return new Tile(TORCHE, 'i', 0xEE0000);
		}

	}

}
