package com.renaud.rogue.game.layout;

public class GridLayout<U> extends LayoutComposite {

    private static int marge = 4;
    private int size;
    private int gridWidth;
    private int gridHeight;

    private U[] leaves;

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

	    this.addChild(new LayoutLeaf(color, x + marge + xi * (gridSize + marge),
		    y + marge + yi * (gridSize + marge), gridSize, gridSize, this));
	}
    }

    public void setLeaf(U leaf, int i, int j) {
	leaves[i + j * gridWidth] = leaf;
    }

    public U getLeaf(int i, int j) {
	return leaves[i + j * gridWidth];
    }

}
