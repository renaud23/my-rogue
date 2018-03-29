package com.renaud.rogue.layout.loot;

import com.renaud.rogue.layout.LayoutComposite;
import com.renaud.rogue.layout.loot.item.GridInventoryItemListener;

public class InventoryLayout extends LayoutComposite {

	private int tileSize = 32;
	private GridInventoryItemLayout inventoryItems;
	private GridInventoryItemLayout weapons;

	public InventoryLayout(int x, int y, int largeur, int hauteur) {
		super(x, y, largeur, hauteur);
		this.color = 0x505050;
		this.inventoryItems = new GridInventoryItemLayout(x + 10, y + 10, 5, 5, tileSize, this, 0x900000, 0x000090);
		this.inventoryItems.addGridListener(new GridInventoryItemListener());
		this.addChild(this.inventoryItems);
	}

	public boolean isOpened() {
		return openedChild != null;
	}

}
