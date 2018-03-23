package com.renaud.rogue.world.dungeon;

import java.util.ArrayList;
import java.util.List;

import com.renaud.rogue.tools.Point;
import com.renaud.rogue.world.Tile;

public class RoomCrowler implements Crowler {

	Dungeon e2;
	List<Point> room = new ArrayList<>();

	public RoomCrowler(Dungeon e2) {
		this.e2 = e2;
	}

	@Override
	public void crowl(int x, int y) {
		room.add(new Point(x, y));
		e2.setTile(x, y, Tile.Factory.getWall());
	}

	public List<Point> getRoom() {
		return room;
	}
}