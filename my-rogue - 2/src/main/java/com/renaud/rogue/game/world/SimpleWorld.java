package com.renaud.rogue.game.world;

import java.io.PrintStream;
import java.util.List;

import com.renaud.rogue.game.element.Monster;
import com.renaud.rogue.game.element.PhysicalLightSource;
import com.renaud.rogue.game.tools.Point;
import com.renaud.rogue.game.world.dungeon.Dungeon;
import com.renaud.rogue.game.world.dungeon.ExtentedDungeon;
import com.renaud.rogue.game.world.dungeon.ExtentedDungeonFactory;

public class SimpleWorld implements World {

	private int width;
	private int height;
	private int size;
	private Dungeon dungeon;

	public SimpleWorld(int width, int height) {
		this.width = width;
		this.height = height;
		this.size = this.width * this.height;

		this.dungeon = ExtentedDungeonFactory
			.newInstance(width, height, 1)
			.buildCave(5)
			.divideFacility(2)
			.combine()
			.carveAccess()
			.lighting(2)
			.addMonsters(1)
			.build();
	}

	public Point peekEmptyPlace() {
		if (dungeon instanceof ExtentedDungeon) {
			return ((ExtentedDungeon) dungeon).peekOutsideFloor();
		}
		return dungeon.peekRandomOne(TileDungeon.FLOOR);
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

	public TileDungeon getTile(int i, int j) {
		if (i < 0 || j < 0 || i >= getWidth() || j >= getHeight())
			return null;
		return dungeon.getTile(i, j);
	}

	public void setTile(int i, int j, TileDungeon tile) {
		if (i < 0 || j < 0 || i >= getWidth() || j >= getHeight())
			return;
		this.dungeon.setTile(i, j, tile);
	}

	public TileDungeon getTile(int i) {
		return dungeon.getTile(i);
	}

	public List<PhysicalLightSource> getTorches() {
		return dungeon.getDungeonLightSource();
	}

	public void print(PrintStream out, boolean element) {
		this.dungeon.print(out, element);
	}

	public List<Monster> getMonsters() {
		return dungeon.getMonsters();
	}

}
