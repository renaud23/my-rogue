package com.renaud.rogue.game.layout;

import com.renaud.rogue.game.inventaire.Item;

public class ItemInventory extends LayoutLeaf {

    private int i;
    private int j;
    private Item item;

    public ItemInventory(int i, int j, int pixelx, int pixely, int largeur, int hauteur, LayoutComposite parent) {
	super(0x000050, pixelx, pixely, largeur, hauteur, parent);
	this.i = i;
	this.j = j;
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
