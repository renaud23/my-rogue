
package com.renaud.rogue.element;

import com.renaud.rogue.drawer.sprite.HeroSprite;
import com.renaud.rogue.drawer.sprite.TorcheSprite;
import com.renaud.rogue.drawer.tile.DoorClosedTile;
import com.renaud.rogue.drawer.tile.DoorOpenedTile;
import com.renaud.rogue.drawer.tile.FireballTile;
import com.renaud.rogue.drawer.tile.GhoulTile;
import com.renaud.rogue.drawer.tile.RogueTile;
import com.renaud.rogue.drawer.tile.WolfTile;

public class TileElement {

	private char charCode;
	private RogueTile tile;
	private int color;

	public TileElement() {}

	public TileElement(char charCode, int color, RogueTile tile) {
		this.charCode = charCode;
		this.tile = tile;
		this.color = color;
	}

	public static class Factory {

		public static TileElement createPlayer() {
			return new TileElement('@', 0xEEEE00, new HeroSprite());
		}

		public static TileElement getWolf() {
			return new TileElement('W', 0xAA0000, new WolfTile());
		}

		public static TileElement getGhoul() {
			return new TileElement('G', 0x0000AA, new GhoulTile());
		}

		public static TileElement getFireball() {
			return new TileElement('*', 0xAAAA00, new FireballTile());
		}

		public static TileElement getTorche() {
			return new TileElement('i', 0xEE0000, new TorcheSprite());
		}

		public static TileElement createClosedDoor() {
			return new TileElement('D', 0xEE2050, new DoorClosedTile());
		}

		public static TileElement createOpenedDoor() {
			return new TileElement('|', 0xEE2050, new DoorOpenedTile());
		}

	}

	public char getCharCode() {
		return charCode;
	}

	public void setCharCode(char charCode) {
		this.charCode = charCode;
	}

	public RogueTile getTile() {
		return tile;
	}

	public void setTile(RogueTile tile) {
		this.tile = tile;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

}
