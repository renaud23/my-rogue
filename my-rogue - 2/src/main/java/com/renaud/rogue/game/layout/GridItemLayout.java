package com.renaud.rogue.game.layout;

import com.renaud.rogue.game.inventaire.Item;

public class GridItemLayout extends GridLayout<Item> {

    private int gridWidth;
    private int gridHeight;
    private int size;

    public GridItemLayout(int x, int y, int gridWidth, int gridHeight, int gridSize, LayoutComposite parent, int color,
	    int colorItem) {
	super(colorItem, x, y, gridWidth, gridHeight, gridSize);
	this.color = color;
	this.parent = parent;
	this.gridWidth = gridWidth;
	this.gridHeight = gridHeight;
	this.size = this.gridWidth * this.gridHeight;
    }

    public void empty() {
	for (int i = 0; i < size; i++) {
	    setLeaf(null, i % gridWidth, i / gridWidth);
	}
    }

}
