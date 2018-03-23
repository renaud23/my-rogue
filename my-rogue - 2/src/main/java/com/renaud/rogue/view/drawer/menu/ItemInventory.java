package com.renaud.rogue.view.drawer.menu;

import com.renaud.rogue.inventaire.Item;

public class ItemInventory extends Layout {

	private int i;
	private int j;
	private Item item;

	public ItemInventory(int i, int j, int pixelx, int pixely, int largeur, int hauteur) {
		super(pixelx, pixely, largeur, hauteur);

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

}
