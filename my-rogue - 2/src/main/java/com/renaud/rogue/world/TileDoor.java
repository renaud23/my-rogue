package com.renaud.rogue.world;

import com.renaud.rogue.drawer.tile.DoorTileImg;
import com.renaud.rogue.drawer.tile.RogueTile;

public class TileDoor extends Tile {

	private static RogueTile doorTile = new DoorTileImg();
	private boolean closed = true;
	private boolean locked = false;

	public TileDoor(long code, char tile, int color) {
		super(code, tile, color, doorTile);
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
}
