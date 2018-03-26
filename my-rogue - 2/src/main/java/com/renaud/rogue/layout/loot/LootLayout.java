package com.renaud.rogue.layout.loot;

import com.renaud.rogue.game.inventaire.Inventaire;
import com.renaud.rogue.game.inventaire.Item;
import com.renaud.rogue.game.world.TileDungeon;
import com.renaud.rogue.layout.GridLayoutListener;
import com.renaud.rogue.layout.LayoutComposite;
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

	this.tilesItems.addGridListener(new GridLayoutListener<Item>() {

	    public void weaponAction(Item item, int i, int j) {
		la.activateTilesItems(item, i, j);
	    }

	    @Override
	    public void over(Item item, int i, int j) {
		la.overTilesItems(item, i, j);
	    }
	});

	this.inventoryItems.addGridListener(new GridLayoutListener<Item>() {

	    public void weaponAction(Item item, int i, int j) {
		la.activateInventoryItems(item, i, j);
	    }

	    @Override
	    public void over(Item item, int i, int j) {
		la.overInventoryItems(item, i, j);
	    }
	});
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
	i = 0;
	for (Item it : inventory) {
	    this.inventoryItems.setLeaf(it, i % 5, i / 5);
	    i++;
	}
    }

    public boolean isOpened() {
	return openedChild != null;
    }

    public void activateTilesItems(Item item, int i, int j) {
	if (item != null) {
	    this.changed = true;
	    if (!inventory.isFull()) {
		GameConsoleDrawer.addLine("Vous vous emparez de " + item.getDesription(), 0x0000FF);
		tile.removeItem(item);
		tilesItems.setLeaf(null, i, j);
		inventory.addItem(item);
		inventoryItems.setFirstEmpty(item);

	    } else {
		GameConsoleDrawer.addLine("Votre inventaire est plein", 0x0000FF);
	    }
	}
    }

    public void overTilesItems(Item u, int i, int j) {
	if (u != null) {
	    GameConsoleDrawer.addLine(u.getDesription(), 0x0000FF);
	}
    }

    public void activateInventoryItems(Item u, int i, int j) {
	if (u != null) {
	    // GameConsoleDrawer.addLine(u.getDesription(), 0x0000FF);
	}
    }

    public void overInventoryItems(Item u, int i, int j) {
	if (u != null) {
	    GameConsoleDrawer.addLine(u.getDesription(), 0x0000FF);
	}
    }

}
