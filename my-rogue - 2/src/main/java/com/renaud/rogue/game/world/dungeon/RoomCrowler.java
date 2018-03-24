package com.renaud.rogue.game.world.dungeon;

import java.util.ArrayList;
import java.util.List;

import com.renaud.rogue.game.tools.Point;
import com.renaud.rogue.game.world.TileDungeon;

public class RoomCrowler implements Crowler {

	Dungeon e2;
	List<Point> room = new ArrayList<>();

	public RoomCrowler(Dungeon e2) {
		this.e2 = e2;
	}

	@Override
	public void crowl(int x, int y) {
		room.add(new Point(x, y));
		e2.setTile(x, y, TileDungeon.Factory.getWall());
	}

	public List<Point> getRoom() {
		return room;
	}
}