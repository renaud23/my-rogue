package com.renaud.rogue.view.drawer.menu;

import com.renaud.rogue.game.inventaire.Item;

public class ItemInventory extends Layout {

    private int i;
    private int j;
    private Item item;

    public ItemInventory(int i, int j, int pixelx, int pixely, int largeur, int hauteur, Layout parent) {
	super(pixelx, pixely, largeur, hauteur, parent);
	this.i = i;
	this.j = j;
	this.color = 0x000050;
    }

    public void setItem(Item item) {
	this.item = item;
    }

    public boolean isEmpty() {
	return item == null;
    }

    public Item removeItem() {
	item = null;
	return item;
    }

    @Override
    public void weaponAction() {
	System.out.println("item pressed " + i + " " + j);
    }

    public String toString() {
	return "item inventory pressed " + i + " " + j;
    }
}
