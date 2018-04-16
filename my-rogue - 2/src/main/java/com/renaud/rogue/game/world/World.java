package com.renaud.rogue.game.world;

import java.io.PrintStream;
import java.util.List;

import com.renaud.rogue.game.element.Element;
import com.renaud.rogue.game.element.Monster;
import com.renaud.rogue.game.element.PhysicalLightSource;
import com.renaud.rogue.game.tools.MathTools;
import com.renaud.rogue.game.tools.Point;

public interface World {

	List<Monster> getMonsters();

	Point peekEmptyPlace();

	void print(PrintStream out, boolean element);

	TileDungeon getTile(int i, int j);

	void setTile(int i, int j, TileDungeon tile);

	TileDungeon getTile(int i);

	List<PhysicalLightSource> getTorches();

	int getSize();

	int getWidth();

	int getHeight();

	default void setElement(Element element, int i, int j) {
		if (this.getTile(i, j).isEmpty()) {
			this.getTile(i, j).setOccupant(element);
		}
	}

	default void removeElement(int i, int j) {
		this.getTile(i, j).setOccupant(null);
	}

	default boolean canGo(int x, int y) {
		if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight()) {
			return false;
		}
		return getTile(x, y).canWalkOn();
	}

	default boolean canSee(Monster pnj, Element cible) {
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

}
