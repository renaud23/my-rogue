package com.renaud.rogue.game.world.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.renaud.rogue.game.element.light.TorcheFixe;
import com.renaud.rogue.game.tools.Point;
import com.renaud.rogue.game.world.TileDungeon;

public class ExtentedDungeon extends AbstractDungeon {

	private List<Point> floorsCave = new ArrayList<>();
	private List<Point> floorsFacility = new ArrayList<>();

	public ExtentedDungeon(int width, int height) {
		super(width, height);
	}

	public void addTorche(int x, int y) {
		this.torches.add(new TorcheFixe(x, y));
	}

	public TileDungeon peekRandomCaveFloor() {
		Random rnd = new Random();
		Point point = null;
		while (point == null) {
			point = floorsCave.get(rnd.nextInt(floorsCave.size()));
			TileDungeon tile = getTile(point.x, point.y);
			if (tile.getCode() != TileDungeon.FLOOR || !tile.isEmpty()) {
				point = null;
			}
		}

		return this.getTile(point.x, point.y);
	}

	public ExtentedDungeon clone() {
		ExtentedDungeon d = new ExtentedDungeon(width, height);
		for (int i = 0; i < size; i++) {
			d.setTile(i, tiles[i].clone());
		}
		return d;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getSize() {
		return size;
	}

	public List<Point> getFloors() {
		return floors;
	}

	public void setFloors(List<Point> floors) {
		this.floors = floors;
	}

	public List<Point> getDoors() {
		return doors;
	}

	public void setDoors(List<Point> doors) {
		this.doors = doors;
	}

	public List<Point> getFloorsCave() {
		return floorsCave;
	}

	public void setFloorsCave(List<Point> floorsCave) {
		this.floorsCave = floorsCave;
	}

	public List<Point> getFloorsFacility() {
		return floorsFacility;
	}

	public void setFloorsFacility(List<Point> floorsFacility) {
		this.floorsFacility = floorsFacility;
	}

}
