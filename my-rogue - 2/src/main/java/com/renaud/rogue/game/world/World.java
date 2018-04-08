package com.renaud.rogue.game.world;

import java.io.PrintStream;
import java.util.List;

import com.renaud.rogue.game.element.Element;
import com.renaud.rogue.game.element.Monster;
import com.renaud.rogue.game.element.PhysicalLightSource;
import com.renaud.rogue.game.tools.MathTools;
import com.renaud.rogue.game.tools.Point;
import com.renaud.rogue.game.world.dungeon.Dungeon;
import com.renaud.rogue.game.world.dungeon.ExtentedDungeon;
import com.renaud.rogue.game.world.dungeon.ExtentedDungeonFactory;

public class World {

	private int width;
	private int height;
	private int size;
	private Dungeon dungeon;

	public World(int width, int height) {
		this.width = width;
		this.height = height;
		this.size = this.width * this.height;

		this.dungeon = ExtentedDungeonFactory.newInstance(width, height).buildCave(5).divideFacility(3).combine().carveAccess().lighting(2).build();

		// this.dungeon = FacilityDungeonProvider.newInstance(width, height).divide(4).build();

		// this.dungeon = SmoothDungeonProvider
		// .newInstance(width, height)
		// .setNbStep(5)
		// .carve()
		// .lighting()
		// .buildEscapeRoom(10, 10)
		// .build();
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

	public void setElement(Element element, int i, int j) {
		if (this.dungeon.getTile(i, j).isEmpty()) {
			this.dungeon.getTile(i, j).setOccupant(element);
		}
	}

	public void removeElement(int i, int j) {
		this.dungeon.getTile(i, j).setOccupant(null);
	}

	public boolean canGo(int x, int y) {
		if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight()) {
			return false;
		}
		return getTile(x, y).canWalkOn();
	}

	public boolean canGo(int ax, int ay, int bx, int by) {
		List<Point> points = MathTools.getSegment(ax, ay, bx, by);
		for (Point p : points) {
			if (p.x < 0 || p.y < 0 || p.x >= getWidth() || p.y >= getHeight()) {
				continue;
			}
			if ((p.x == ax && p.y == ay) || (p.x == bx && p.y == by)) {
				continue;
			}
			if (!this.getTile(p.x, p.y).canWalkOn()) {
				return false;
			}
		}
		return true;
	}

	public boolean isInWorld(int x, int y) {
		if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight())
			return false;
		return true;
	}

	public boolean canSee(Monster pnj, Element cible) {
		int dist = MathTools.distance(pnj.getX(), pnj.getY(), cible.getX(), cible.getY());
		if (dist > pnj.getDepht() * pnj.getDepht())
			return false;
		List<Point> points = MathTools.getSegment(pnj.getX(), pnj.getY(), cible.getX(), cible.getY());
		for (Point p : points) {
			if ((p.x == pnj.getX() && p.y == pnj.getY()) || (p.x == cible.getX() && p.y == cible.getY())) {
				continue;
			}
			if (!this.getTile(p.x, p.y).canSeeThrought()) {
				return false;
			}

		}
		return true;
	}

	public List<PhysicalLightSource> getTorches() {
		return dungeon.getDungeonLightSource();
	}

	public void print(PrintStream out, boolean element) {
		this.dungeon.print(out, element);
	}

	public Dungeon getDungeon() {
		return dungeon;
	}

}
