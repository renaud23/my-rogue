package com.renaud.rogue.game.world.dungeon;

import java.util.ArrayList;
import java.util.List;

import com.renaud.rogue.game.tools.Point;
import com.renaud.rogue.game.tools.Rectangle;

public class Facility extends AbstractDungeon {

	private List<Rectangle> rooms = new ArrayList<>();

	public Facility(int width, int height) {
		super(width, height);
	}

	public Facility clone() {
		Facility d = new Facility(width, height);
		for (int i = 0; i < size; i++) {
			d.setTile(i, tiles[i].clone());
		}
		return d;
	}

	public List<Rectangle> getRooms() {
		return rooms;
	}

	public void setRooms(List<Rectangle> rooms) {
		this.rooms = rooms;
	}

	public void setFloors(List<Point> floors) {
		this.floors = floors;
	}

}
