
package com.renaud.rogue.game.element;

import com.renaud.rogue.view.drawer.sprite.HeroSprite;
import com.renaud.rogue.view.drawer.sprite.TorcheSprite;
import com.renaud.rogue.view.drawer.tile.FireballTile;
import com.renaud.rogue.view.drawer.tile.GhoulTile;
import com.renaud.rogue.view.drawer.tile.GunAmmoTile;
import com.renaud.rogue.view.drawer.tile.GunTile;
import com.renaud.rogue.view.drawer.tile.KnifeTile;
import com.renaud.rogue.view.drawer.tile.RogueTile;
import com.renaud.rogue.view.drawer.tile.WolfTile;

public class TileElement {

    private char charCode;
    private RogueTile tileImage;
    private int color;

    public TileElement() {
    }

    public TileElement(char charCode, int color, RogueTile tile) {
	this.charCode = charCode;
	this.tileImage = tile;
	this.color = color;
    }

    public TileElement(char charCode, int color) {
	this.charCode = charCode;
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

	public static TileElement createGunAmmo() {
	    return new TileElement('u', 0xEE0000, new GunAmmoTile());
	}

	public static TileElement createGun() {
	    return new TileElement('d', 0xEEEEEE, new GunTile());
	}

	public static TileElement createKnife() {
	    return new TileElement('k', 0xEEEEEE, new KnifeTile());
	}

    }

    public char getCharCode() {
	return charCode;
    }

    public void setCharCode(char charCode) {
	this.charCode = charCode;
    }

    public RogueTile getTileImage() {
	return tileImage;
    }

    public void setTile(RogueTile tile) {
	this.tileImage = tile;
    }

    public int getColor() {
	return color;
    }

    public void setColor(int color) {
	this.color = color;
    }

}
