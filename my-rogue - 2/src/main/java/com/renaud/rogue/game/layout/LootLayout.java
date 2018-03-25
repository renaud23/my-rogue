package com.renaud.rogue.game.layout;

import com.renaud.rogue.game.inventaire.Inventaire;
import com.renaud.rogue.game.inventaire.Item;
import com.renaud.rogue.game.world.TileDungeon;
import com.renaud.rogue.view.drawer.GameConsoleDrawer;

public class LootLayout extends LayoutComposite {

    private int tileSize = 32;
    private Inventaire inventory;
    private TileDungeon tile;
    private GridItemLayout inventoryItems;
    private GridItemLayout tilesItems;

    public LootLayout(int x, int y, int largeur, int hauteur) {
	super(x, y, largeur, hauteur);
	this.color = 0x505050;
	this.inventoryItems = new GridItemLayout(x + 10, y + 10, 5, 5, tileSize, this, 0x900000, 0x000090);
	this.tilesItems = new GridItemLayout(x + 20 + 5 * (tileSize + 4), y + 10, 2, 5, tileSize, this, 0x900000,
		0x000090);
	this.addChild(this.inventoryItems);
	this.addChild(this.tilesItems);

	final LootLayout la = this;

	// this.tilesItems.addListener(new LayoutListener() {
	// public void activate(Layout l) {
	// // la.activateTilesItems;
	// }
	// });
    }

    public void initialise(Inventaire inventaire, TileDungeon tile) {
	this.inventory = inventaire;
	this.tile = tile;

	this.inventoryItems.empty();
	this.tilesItems.empty();
	int i = 0;
	for (Item it : tile.getItems()) {
	    this.tilesItems.setLeaf(it, i % 2, i / 2);
	    i++;
	}
    }

    public boolean isOpened() {
	return openedChild != null;
    }

    public void activateTilesItems(Item u) {
	GameConsoleDrawer.addLine(u.getDesription(), 0x0000FF);
    }

}
