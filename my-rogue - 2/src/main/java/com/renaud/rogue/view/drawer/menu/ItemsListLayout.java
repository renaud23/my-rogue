package com.renaud.rogue.view.drawer.menu;

public class ItemsListLayout extends Layout {

    private int gridSize = 25;
    private ItemInventory[] items = new ItemInventory[gridSize];

    public ItemsListLayout(int x, int y, int largeur, int hauteur, Layout parent) {
	super(x, y, largeur, hauteur, parent);
	this.color = 0x500000;
	for (int i = 0; i < gridSize; i++) {
	    int xi = i % 5;
	    int yi = i / 5;
	    items[i] = new ItemInventory(xi, yi, x + 4 + xi * 20, y + 4 + yi * 20, 16, 16, parent);
	    this.addChild(items[i]);
	}
    }

    // @Override
    // public void setDrawOperation(IDrawOperation op) {
    // super.setDrawOperation(op);
    // for (int i = 0; i < gridSize; i++) {
    // items[i].setDrawOperation(op);
    // }
    // }

    public String toString() {
	return "ItemsListLayout";
    }

}
