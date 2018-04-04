package com.renaud.rogue.game.world.dungeon;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import com.renaud.rogue.game.element.PhysicalLightSource;
import com.renaud.rogue.game.tools.Point;
import com.renaud.rogue.game.world.TileDungeon;

public abstract class AbstractDungeon implements Dungeon {

	protected int width;
	protected int height;
	protected int size;
	protected TileDungeon[] tiles;

	protected List<PhysicalLightSource> lightsources = new ArrayList<>();

	protected List<Point> floors = new ArrayList<>();
	protected List<Point> walls = new ArrayList<>();
	protected List<Point> doors = new ArrayList<>();

	public AbstractDungeon(int width, int height) {
		this.width = width;
		this.height = height;
		this.width = width;
		this.height = height;
		this.size = this.width * this.height;
		this.tiles = new TileDungeon[this.size];
	}

	public Point peekRandomOne(long tileCode) {
		Random rnd = new Random();
		Point point = null;
		while (point == null) {
			point = floors.get(rnd.nextInt(floors.size()));
			TileDungeon tile = getTile(point.x, point.y);
			if (tile.getCode() != tileCode || !tile.isEmpty()) {
				point = null;
			}
		}

		return point;
	}

	public void print(PrintStream out, boolean element) {
		for (int i = 0; i < size; i++) {
			if (!tiles[i].isEmpty() && element) {
				out.print(tiles[i].getOccupant().getTile().getCharCode());
			} else
				out.print(tiles[i].getCharCode());

			if ((i % width) == (width - 1)) {
				out.println();
			}
		}
	}

	public void fill(long type) {
		for (int i = 0; i < size; i++) {
			if (type == TileDungeon.WALL) {
				tiles[i] = TileDungeon.Factory.createWall();
			} else if (type == TileDungeon.UNKNOW) {
				tiles[i] = TileDungeon.Factory.getUnknow();
			} else {
				if (type == TileDungeon.FLOOR) {
					tiles[i] = TileDungeon.Factory.createfloor();
				}
			}
		}
	}

	public TileDungeon getTile(int i, int j) {
		return tiles[i + j * width];
	}

	public TileDungeon getTile(int i) {
		return tiles[i];
	}

	public void setTile(int i, int j, TileDungeon tile) {
		tiles[i + j * width] = tile;
	}

	public void setTile(int i, TileDungeon tile) {
		tiles[i] = tile;
	}

	public void setDungeonLightsource(List<PhysicalLightSource> lightsources) {
		this.lightsources = lightsources;
	}

	public List<PhysicalLightSource> getDungeonLightSource() {
		return lightsources;
	}

	public List<Point> getFloors() {
		return floors;
	}

	public List<Point> getWalls() {
		return walls;
	}

	public void setWalls(List<Point> walls) {
		this.walls = walls;
	}

	/* */
	public void crowl(Point start, Crowler c) {
		Set<Point> visited = new HashSet<>();
		Stack<Point> stack = new Stack<>();

		stack.push(start);
		visited.add(start);

		while (stack.size() > 0) {
			Point curr = stack.pop();

			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if (i == 0 && j == 0) {
						c.crowl(curr.x, curr.y);
						continue;
					} else if (i == 0 || j == 0) {
						Point p = new Point(curr.x + i, curr.y + j);
						if (!visited.contains(p) && getTile(p.x, p.y).canWalkOn()) {
							stack.push(p);
							visited.add(p);

						}
					}
				}
			}
		}
	}

}
