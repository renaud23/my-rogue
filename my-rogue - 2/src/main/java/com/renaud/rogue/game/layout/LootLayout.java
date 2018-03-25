package com.renaud.rogue.game.layout;

public class LootLayout extends LayoutComposite {

    private GridItemLayout inventoryItems;
    private GridItemLayout tilesItems;

    public LootLayout(int x, int y, int largeur, int hauteur) {
	super(x, y, largeur, hauteur);
	this.color = 0x505050;

	this.inventoryItems = new GridItemLayout(x + 10, y + 10, 5, 5, 16, this, 0x900000, 0x000090);
	this.tilesItems = new GridItemLayout(x + 20 + 5 * 20, y + 10, 2, 5, 16, this, 0x900000, 0x000090);

	this.addChild(this.inventoryItems);
	this.addChild(this.tilesItems);

    }

    public boolean isOpened() {
	return openedChild != null;
    }

}
