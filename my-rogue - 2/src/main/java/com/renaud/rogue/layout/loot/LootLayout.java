package com.renaud.rogue.layout.loot;

import com.renaud.rogue.game.inventaire.Item;
import com.renaud.rogue.game.sequence.Game;
import com.renaud.rogue.game.world.TileDungeon;
import com.renaud.rogue.layout.GridLayoutListener;
import com.renaud.rogue.layout.LayoutComposite;
import com.renaud.rogue.layout.loot.item.GridInventoryItemListener;
import com.renaud.rogue.layout.loot.item.ItemLayout;

public class LootLayout extends LayoutComposite implements GridLayoutListener<ItemLayout> {

	private int tileSize = 32;
	private Game game;
	private TileDungeon tile;

	private GridInventoryItemLayout inventoryItems;
	private GridInventoryItemLayout tilesItems;
	private GridInventoryItemLayout weapons;

	public LootLayout(int x, int y, int largeur, int hauteur) {
		super(x, y, largeur, hauteur);
		this.color = 0x505050;
		this.tilesItems = new GridInventoryItemLayout(x + 20 + 5 * (tileSize + 4), y + 10, 2, 5, tileSize, this, 0x900000, 0x000090);
		this.inventoryItems = new GridInventoryItemLayout(x + 10, y + 10, 5, 5, tileSize, this, 0x900000, 0x000090);
		this.weapons = new GridInventoryItemLayout(x + 10, y + 20 + 5 * (tileSize + 4), 2, 1, tileSize, this, 0x900000, 0x000090);
		this.tilesItems.addGridListener(new GridInventoryItemListener());
		this.inventoryItems.addGridListener(new GridInventoryItemListener());
		this.weapons.addGridListener(new GridInventoryItemListener());
		this.tilesItems.addGridListener(this);
		this.inventoryItems.addGridListener(this);
		this.weapons.addGridListener(this);
		this.addChild(this.tilesItems);
		this.addChild(this.inventoryItems);
		this.addChild(this.weapons);
	}

	public void refresh() {
		this.inventoryItems.empty();
		this.tilesItems.empty();
		int i = 0;
		for (Item item : tile.getItems()) {
			this.tilesItems.setLeaf(ItemLayout.Factory.createLoot(game, item), i % 2, i / 2);
			i++;
		}
		i = 0;
		for (Item item : game.getJoueur().getInventory()) {
			this.inventoryItems.setLeaf(ItemLayout.Factory.createInventory(game, item), i % 5, i / 5);
			i++;
		}

		this.weapons.setLeaf(ItemLayout.Factory.createWeaponRack(game.getJoueur().getMeleeWeapon()), 0, 0);
		this.weapons.setLeaf(ItemLayout.Factory.createWeaponRack(game.getJoueur().getRankedWeapon()), 1, 0);
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

	@Override
	public void weaponAction(ItemLayout u, int i, int j) {
		this.refresh();
	}

	@Override
	public void activateAction(ItemLayout u, int i, int j) {
		this.refresh();
	}

	@Override
	public void switchWeaponAction(ItemLayout u, int i, int j) {
		this.refresh();
	}

	@Override
	public void inventaireAction(ItemLayout u, int i, int j) {
		this.refresh();
	}

	@Override
	public void over(ItemLayout u, int i, int j) {
		this.refresh();
	}

	@Override
	public void annulerAction(ItemLayout u, int i, int j) {
		this.refresh();
	}

}
