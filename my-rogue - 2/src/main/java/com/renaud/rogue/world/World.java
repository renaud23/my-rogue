package com.renaud.rogue.world;

import java.io.PrintStream;

import com.renaud.rogue.element.Element;
import com.renaud.rogue.tools.Point;

public class World {

	private int width;
	private int height;
	private int size;
	private Dungeon dungeon;

	public World(int width, int height) {
		this.width = width;
		this.height = height;
		this.size = this.width * this.height;
		this.dungeon = SmoothLevelProvider.newInstance(width, height).setNbStep(5).build();
	}

	public Point peekEmptyPlace() {
		return dungeon.peekRandomOne(Tile.FLOOR);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Tile getTile(int i, int j) {
		return dungeon.getTile(i, j);
	}

	public Tile getTile(int i) {
		return dungeon.getTile(i);
	}

	public void setElement(int i, int j, Element element) {
		this.dungeon.getTile(i, j).setElement(element);
	}

	public void print(PrintStream out) {
		for (int i = 0; i < size; i++) {
			Tile tile = dungeon.getTile(i);
			if (tile.isEmpty()) {
				out.print(tile.getTile());
			}
			else {
				out.print(tile.getElement().getTile().getTile());
			}

			if ((i % width) == (width - 1)) {
				out.println();
			}
		}
	}
}
