package com.renaud.rogue.element;

public interface Element {

	int getX();

	int getY();

	default void setX(int x) {};

	default void setY(int y) {};

	default TileElement getTile() {
		return null;
	};

	default boolean isOpaque() {
		return false;
	};

}
