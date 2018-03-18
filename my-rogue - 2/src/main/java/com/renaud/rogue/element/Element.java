package com.renaud.rogue.element;

import com.renaud.rogue.world.Tile;

public interface Element {

    int getX();

    int getY();

    void setX(int x);

    void setY(int y);

    Tile getTile();

    boolean isOpaque();

}
