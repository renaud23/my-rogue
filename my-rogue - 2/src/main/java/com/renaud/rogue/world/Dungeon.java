package com.renaud.rogue.world;

import java.io.PrintStream;
import java.util.Random;

import com.renaud.rogue.tools.Point;

public class Dungeon {

	private int width;
	private int height;
	private int size;
	private Tile[] tiles;
	// TODO liste monstres
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
			}
			else {
				if (type == Tile.FLOOR) {
					tiles[i] = Tile.Factory.getFloor();
				}
			}
		}
	}

	public Point peekRandomOne(long tileCode) {
		Random rnd = new Random();
		boolean find = false;
		int i = -1;
		while (!find) {
			i = rnd.nextInt(size);
			if (tiles[i].getCode() == tileCode && tiles[i].isEmpty()) {
				find = true;
			}
		}

		return new Point(i % width, i / width);
	}

	public Point peekOne(long tileCode) {
		for (int i = 0; i < size; i++) {
			if (tileCode == tiles[i].getCode()) {
				return new Point(i % width, i / width);
			}
		}
		return null;
	}

	public void print(PrintStream out) {

		for (int i = 0; i < size; i++) {
			out.print(tiles[i].getTile());

			if ((i % width) == (width - 1)) {
				out.println();
			}
		}
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

}
