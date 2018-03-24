package com.renaud.rogue.game.inventaire;

import java.util.ArrayList;
import java.util.List;

public class Inventaire {

	private int maxSize = 5;
	private List<Item> items = new ArrayList<>();

	public void addItem(Item item) {
		if (!isFull()) {
			items.add(item);
		}
	}

	public void removeItem(Item item) {
		items.remove(item);
	}

	public boolean isFull() {
		return this.items.size() == maxSize;
	}
}
