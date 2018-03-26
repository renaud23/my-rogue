package com.renaud.rogue.layout.loot;

import com.renaud.rogue.layout.LayoutComposite;

public class InventoryLayout extends LayoutComposite {

    private GridItemLayout itemsListLayout;
    private LayoutComposite second;

    public InventoryLayout(int x, int y, int largeur, int hauteur) {
	super(x, y, largeur, hauteur);
	this.color = 0x505050;
	this.itemsListLayout = new GridItemLayout(x + 10, y + 10, 5, 5, 16, this, 0x900000, 0x000090);
	this.second = new LayoutComposite(x + 120, y + 10, 100, 200, this);

	this.addChild(this.itemsListLayout);
	this.addChild(this.second);
    }

}
