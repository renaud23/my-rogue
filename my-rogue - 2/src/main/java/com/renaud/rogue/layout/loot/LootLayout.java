package com.renaud.rogue.layout.loot;

import com.renaud.rogue.game.inventaire.Item;
import com.renaud.rogue.game.sequence.Game;
import com.renaud.rogue.game.world.TileDungeon;
import com.renaud.rogue.layout.LayoutComposite;
import com.renaud.rogue.layout.loot.item.GridInventoryItemListener;
import com.renaud.rogue.layout.loot.item.ItemLayout;

public class LootLayout extends LayoutComposite {

    private int tileSize = 32;
    private Game game;
    private TileDungeon tile;

    private GridInventoryItemLayout inventoryItems;
    private GridInventoryItemLayout tilesItems;

    public LootLayout(int x, int y, int largeur, int hauteur) {
	super(x, y, largeur, hauteur);
	this.color = 0x505050;
	this.tilesItems = new GridInventoryItemLayout(x + 20 + 5 * (tileSize + 4), y + 10, 2, 5, tileSize, this,
		0x900000, 0x000090);
	this.inventoryItems = new GridInventoryItemLayout(x + 10, y + 10, 5, 5, tileSize, this, 0x900000, 0x000090);
	this.tilesItems.addGridListener(new GridInventoryItemListener());
	this.inventoryItems.addGridListener(new GridInventoryItemListener());
	this.addChild(this.tilesItems);
	this.addChild(this.inventoryItems);
    }

    public void refresh() {
	this.inventoryItems.empty();
	this.tilesItems.empty();
	int i = 0;
	for (Item item : tile.getItems()) {
	    this.tilesItems.setLeaf(ItemLayout.Factory.createLoot(game, tile, item), i % 2, i / 2);
	    i++;
	}
	i = 0;
	for (Item item : game.getJoueur().getInventaire()) {
	    this.inventoryItems.setLeaf(ItemLayout.Factory.createInventory(game, tile, item), i % 5, i / 5);
	    i++;
	}
	this.changed = true;
    }

    public void initialise(Game game, TileDungeon tile) {
	this.game = game;
	this.tile = tile;
	this.refresh();
    }

    public boolean isOpened() {
	return openedChild != null;
    }

    public void weaponAction() {
	super.weaponAction();
	this.refresh();
    }

    @Override
    public void activateAction() {
	super.activateAction();
	this.refresh();
    }

}
