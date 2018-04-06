package com.renaud.rogue.game.world;

import com.renaud.rogue.game.inventaire.KeyDoor;
import com.renaud.rogue.view.drawer.GameConsoleDrawer;
import com.renaud.rogue.view.drawer.tile.DoorClosedTile;
import com.renaud.rogue.view.drawer.tile.DoorOpenedTile;
import com.renaud.rogue.view.drawer.tile.RogueTile;

public class TileDoor extends TileDungeon implements Activable {

	private static RogueTile openDoorTile = new DoorOpenedTile();
	private static RogueTile closeDoorTile = new DoorClosedTile();
	private boolean closed = true;
	private boolean locked = false;
	private int keyCode;

	public TileDoor(long code, char tile, int color) {
		super(code, tile, color, openDoorTile);
	}

	public TileDoor(long code, char tile, int color, int keyCode) {
		super(code, tile, color, openDoorTile);
		this.keyCode = keyCode;
	}

	public boolean isClose() {
		return closed;
	}

	public boolean isOpen() {
		return !closed;
	}

	public void open() {
		closed = false;
	}

	public void close() {
		closed = true;
	}

	public boolean isLocked() {
		return locked;
	}

	public void unlock() {
		locked = false;
	}

	public void lock() {
		locked = true;
	}

	public boolean unlock(KeyDoor key) {
		if (key.getCode() == keyCode) {
			locked = false;
			return true;
		}
		return false;
	}

	@Override
	public void activate(Game game, int x, int y) {
		if (!locked) {
			this.closed = !closed;
			GameConsoleDrawer.info("La porte grince en s'ouvrant.");
		} else {
			GameConsoleDrawer.info("Cette porte est vérrouillée.");
		}
	}

	@Override
	public RogueTile getTile() {
		return closed ? closeDoorTile : openDoorTile;
	}
}
