package com.renaud.rogue.world;

import com.renaud.rogue.sequence.Game;
import com.renaud.rogue.view.drawer.tile.DoorClosedTile;
import com.renaud.rogue.view.drawer.tile.DoorOpenedTile;
import com.renaud.rogue.view.drawer.tile.RogueTile;

public class TileDoor extends Tile implements Activable {

	private static RogueTile openDoorTile = new DoorOpenedTile();
	private static RogueTile closeDoorTile = new DoorClosedTile();
	private boolean closed = true;
	private boolean locked = false;

	public TileDoor(long code, char tile, int color) {
		super(code, tile, color, openDoorTile);
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

	@Override
	public void activate(Game game, int x, int y) {
		this.closed = !closed;
	}

	@Override
	public RogueTile getTile() {
		return closed ? closeDoorTile : openDoorTile;
	}
}
