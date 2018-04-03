package com.renaud.rogue.game.inventaire;

import com.renaud.rogue.game.element.TileElement;

public class KeyDoor implements Item {

	private final static TileElement tile = TileElement.Factory.createKeyDoor();

	private int code;

	public KeyDoor(int code) {
		this.code = code;
	}

	@Override
	public TileElement getTile() {
		return tile;
	}

	@Override
	public String getDesription() {
		return "La clef d'une porte.";
	}

	public int getCode() {
		return code;
	}

}
