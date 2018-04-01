package com.renaud.rogue.game.world.dungeon;

import java.io.PrintStream;
import java.util.List;

import com.renaud.rogue.game.element.light.TorcheFixe;
import com.renaud.rogue.game.tools.Point;
import com.renaud.rogue.game.world.TileDungeon;

public interface Dungeon {

	TileDungeon getTile(int i, int j);

	TileDungeon getTile(int i);

	Point peekRandomOne(long code);

	void setTile(int i, int j, TileDungeon tile);

	List<TorcheFixe> getTorches();

	void print(PrintStream out, boolean element);

	void fill(long type);

}
