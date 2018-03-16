package com.renaud.rogue.element;

import com.renaud.rogue.world.Tile;

public interface Element {

	int getX();

	int getY();

	int getDepht();

	Tile getTile();

	boolean isOpaque();

}
