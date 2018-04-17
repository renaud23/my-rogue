package com.renaud.rogue.game.world;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.renaud.rogue.game.element.Monster;
import com.renaud.rogue.game.element.PhysicalLightSource;
import com.renaud.rogue.game.tools.Point;
import com.renaud.rogue.game.world.dungeon.Dungeon;
import com.renaud.rogue.game.world.dungeon.ExtentedDungeon;
import com.renaud.rogue.game.world.dungeon.ExtentedDungeonFactory;

public class LargeWorld implements World {

	private int width;
	private int height;
	private int size;
	private List<Dungeon> dungeons = new ArrayList<>();
	private Dungeon current;

	public LargeWorld(int width, int height) {
		this.width = width;
		this.height = height;
		this.size = this.width * this.height;

		// this.dungeon = ExtentedDungeonFactory
		// .newInstance(width, height)
		// .buildCave(5)
		// .divideFacility(2)
		// .combine()
		// .carveAccess()
		// .lighting(2)
		// .addMonsters(1)
		// .build();
		for (int i = 1; i <= 10; i++) {
			Dungeon d = ExtentedDungeonFactory
				.newInstance(width, height, i)
				.buildCave(5)
				.divideFacility(2)
				.combine()
				.carveAccess()
				.lighting(2)
				.addMonsters(1)
				.build();
			dungeons.add(d);
		}
		current = dungeons.get(0);
	}

	public Point peekEmptyPlace() {
		if (current instanceof ExtentedDungeon) {
			return ((ExtentedDungeon) current).peekOutsideFloor();
		}
		return current.peekRandomOne(TileDungeon.FLOOR);
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
		return current.getTile(i, j);
	}

	public void setTile(int i, int j, TileDungeon tile) {
		if (i < 0 || j < 0 || i >= getWidth() || j >= getHeight())
			return;
		this.current.setTile(i, j, tile);
	}

	public TileDungeon getTile(int i) {
		return current.getTile(i);
	}

	public List<PhysicalLightSource> getTorches() {
		return current.getDungeonLightSource();
	}

	public void print(PrintStream out, boolean element) {
		this.current.print(out, element);
	}

	public List<Monster> getMonsters() {
		return current.getMonsters();
	}

}
