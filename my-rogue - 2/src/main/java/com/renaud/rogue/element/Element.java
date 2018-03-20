package com.renaud.rogue.element;

import com.renaud.rogue.world.Tile;

public interface Element {

    int getX();

    int getY();

    default void setX(int x) {
    };

    default void setY(int y) {
    };

    default Tile getTile() {
	return null;
    };

    default boolean isOpaque() {
	return false;
    };

}
