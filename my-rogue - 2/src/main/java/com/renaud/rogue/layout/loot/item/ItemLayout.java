package com.renaud.rogue.layout.loot.item;

import com.renaud.rogue.game.event.ActionEvent;
import com.renaud.rogue.game.inventaire.Item;
import com.renaud.rogue.game.sequence.Game;
import com.renaud.rogue.game.weapon.Weapon;
import com.renaud.rogue.game.world.TileDungeon;

public interface ItemLayout extends ActionEvent {
    default void over() {
    };

    Item getItem();

    public static class Factory {
	public static ItemLayout createLoot(Game game, TileDungeon tile, Item item) {
	    if (item instanceof Weapon) {
		return new ItemLayoutWeapon(item, new LootItemAction(game.getJoueur().getInventaire(), tile, item));
	    }

	    return new ItemLayoutDefault(item, new LootItemAction(game.getJoueur().getInventaire(), tile, item));
	}

	public static ItemLayout createInventory(Game game, TileDungeon tile, Item item) {
	    if (item instanceof Weapon) {
		return new ItemLayoutWeapon(item, new LootItemAction(game.getJoueur().getInventaire(), tile, item),
			new EquipeWeaponAction(game));
	    }

	    return new ItemLayoutDefault(item, new ThrowItemAction(game.getJoueur().getInventaire(), tile, item));
	}
    }
}
