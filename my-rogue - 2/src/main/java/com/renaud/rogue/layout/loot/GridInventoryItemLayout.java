package com.renaud.rogue.layout.loot;

import com.renaud.rogue.layout.GridLayout;
import com.renaud.rogue.layout.LayoutComposite;
import com.renaud.rogue.layout.loot.item.ItemLayout;

public class GridInventoryItemLayout extends GridLayout<ItemLayout> {

    private int gridWidth;
    private int gridHeight;
    private int size;

    public GridInventoryItemLayout(int x, int y, int gridWidth, int gridHeight, int gridSize, LayoutComposite parent,
	    int color, int colorItem) {
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
