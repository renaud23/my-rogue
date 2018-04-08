package com.renaud.rogue.game.world.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.renaud.rogue.game.tools.Point;
import com.renaud.rogue.game.world.TileDungeon;

public class ExtentedDungeon extends AbstractDungeon {

	private List<Point> floorsCave = new ArrayList<>();
	private List<Point> floorsFacility = new ArrayList<>();

	public ExtentedDungeon(int width, int height) {
		super(width, height);
	}

	public TileDungeon peekRandomCaveFloor() {
		Random rnd = new Random();
		Point point = null;
		while (point == null) {
			point = floorsFacility.get(rnd.nextInt(floorsFacility.size()));
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

	public Point peekRandomOne(long tileCode) {
		return peekRandomOne(floors, tileCode);
	}

	private Point peekRandomOne(List<Point> floorsTile, long tileCode) {
		Random rnd = new Random();
		Point point = null;
		while (point == null) {
			point = floorsTile.get(rnd.nextInt(floorsTile.size()));
			TileDungeon tile = getTile(point.x, point.y);
			if (tile.getCode() != tileCode || !tile.isEmpty()) {
				point = null;
			}
		}

		return point;
	}

	public Point peekInsideFloor() {
		return peekRandomOne(floorsFacility, TileDungeon.FLOOR);
	}

	public Point peekOutsideFloor() {
		return peekRandomOne(floorsCave, TileDungeon.FLOOR);
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
