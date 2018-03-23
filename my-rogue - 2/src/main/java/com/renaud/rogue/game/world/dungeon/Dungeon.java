package com.renaud.rogue.world.dungeon;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import com.renaud.rogue.element.light.TorcheFixe;
import com.renaud.rogue.tools.Point;
import com.renaud.rogue.world.Tile;

public class Dungeon {

	private int width;
	private int height;
	private int size;
	private Tile[] tiles;

	private List<TorcheFixe> torches = new ArrayList<>();
	private List<Point> cavern;
	private List<Point> exitRoom;
	private Point exitDoorLocation;

	// TODO liste items

	public Dungeon(int width, int height) {
		this.width = width;
		this.height = height;
		this.size = this.width * this.height;
		this.tiles = new Tile[this.size];
	}

	public void fill(long type) {
		for (int i = 0; i < size; i++) {
			if (type == Tile.WALL) {
				tiles[i] = Tile.Factory.getWall();
			} else if (type == Tile.UNKNOW) {
				tiles[i] = Tile.Factory.getUnknow();
			} else {
				if (type == Tile.FLOOR) {
					tiles[i] = Tile.Factory.getFloor();
				}
			}
		}
	}

	public Point peekRandomOne(long tileCode) {
		Random rnd = new Random();
		Point point = null;
		while (point == null) {
			point = cavern.get(rnd.nextInt(cavern.size()));
			Tile tile = getTile(point.x, point.y);
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

	public void addTorche(int x, int y) {
		this.torches.add(new TorcheFixe(x, y));
	}

	public List<TorcheFixe> getTorches() {
		return torches;
	}

	public Dungeon clone() {
		Dungeon d = new Dungeon(width, height);
		for (int i = 0; i < size; i++) {
			d.setTile(i, tiles[i].clone());
		}
		return d;
	}

	public Tile getTile(int i, int j) {
		return tiles[i + j * width];
	}

	public Tile getTile(int i) {
		return tiles[i];
	}

	public void setTile(int i, int j, Tile tile) {
		tiles[i + j * width] = tile;
	}

	public void setTile(int i, Tile tile) {
		tiles[i] = tile;
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

	public List<Point> getCavern() {
		return cavern;
	}

	public void setCavern(List<Point> cavern) {
		this.cavern = cavern;
	}

	public List<Point> getExitRoom() {
		return exitRoom;
	}

	public void setExitRoom(List<Point> exitRoom) {
		this.exitRoom = exitRoom;
	}

	public Point getExitDoorLocation() {
		return exitDoorLocation;
	}

	public void setExitDoorLocation(Point exitDoorLocation) {
		this.exitDoorLocation = exitDoorLocation;
	}

}
