package com.renaud.rogue.game.layout;

import com.renaud.rogue.game.inventaire.Item;

public class GridItemLayout extends GridLayout<Item> {

    public GridItemLayout(int x, int y, int gridWidth, int gridHeight, int gridSize, LayoutComposite parent, int color,
	    int colorItem) {
	super(colorItem, x, y, gridWidth, gridHeight, gridSize);
	this.color = color;
	this.parent = parent;
    }

}
