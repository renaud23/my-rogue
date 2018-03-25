package com.renaud.rogue.game.layout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GridLayout<U> extends LayoutComposite {

    private List<GridLayoutListener<U>> gridListeners = new ArrayList<>();
    private static int marge = 4;
    private int size;
    private int gridWidth;
    private int gridHeight;

    private U[] leaves;

    private Map<Layout, Integer> access = new HashMap<>();

    @SuppressWarnings("unchecked")
    public GridLayout(int color, int x, int y, int gridWidth, int gridHeight, int gridSize) {
	super(x, y, marge + gridWidth * (gridSize + marge), marge + gridHeight * (gridSize + marge));
	this.size = gridWidth * gridHeight;
	this.leaves = (U[]) new Object[size];

	this.gridWidth = gridWidth;
	this.gridHeight = gridHeight;

	for (int i = 0; i < size; i++) {
	    int xi = i % gridWidth;
	    int yi = i / gridWidth;

	    LayoutLeaf leaf = new LayoutLeaf(color, x + marge + xi * (gridSize + marge),
		    y + marge + yi * (gridSize + marge), gridSize, gridSize, this);
	    leaf.addListener(this);
	    access.put(leaf, i);
	    this.addChild(leaf);
	}
	addListener(this);

    }

    public void setFirstEmpty(U leaf) {
	for (int i = 0; i < size; i++) {
	    if (leaves[i] == null) {
		leaves[i] = leaf;
		break;
	    }
	}
    }

    public void setLeaf(U leaf, int i, int j) {
	leaves[i + j * gridWidth] = leaf;
    }

    public U getLeaf(int i, int j) {
	return leaves[i + j * gridWidth];
    }

    public int getGridWidth() {
	return gridWidth;
    }

    public int getGridHeight() {
	return gridHeight;
    }

    /* */

    public void over(Layout l) {
	int index = access.get(l);
	U u = leaves[index];
	for (GridLayoutListener<U> li : gridListeners) {
	    li.over(u, index % gridWidth, index / gridWidth);
	}
    }

    public void activate(Layout l) {
	int index = access.get(l);
	U u = leaves[index];
	for (GridLayoutListener<U> li : gridListeners) {
	    li.activate(u, index % gridWidth, index / gridWidth);
	}
    }

    public void addGridListener(GridLayoutListener<U> l) {
	this.gridListeners.add(l);
    }

}
