package com.renaud.rogue.layout.loot.item;

import com.renaud.rogue.game.inventaire.Ammunition;
import com.renaud.rogue.game.inventaire.Item;
import com.renaud.rogue.game.inventaire.KeyDoor;
import com.renaud.rogue.game.sequence.Game;
import com.renaud.rogue.game.weapon.Weapon;
import com.renaud.rogue.layout.GridLayoutListener;
import com.renaud.rogue.view.console.Console;

public interface ItemLayout extends GridLayoutListener<ItemLayout> {

	default void over(ItemLayout u, int i, int j) {};

	public Item getItem();

	public static class Factory {

		public static ItemLayout createInventory(Console console, Game game, Item item) {
			ItemLayoutGen l = new ItemLayoutGen(console, item);
			l.setWeaponAction(new ThrowItemAction(game));
			if (item instanceof Ammunition) {
				l.setActivateAction(new ReloadWeaponAction(game));
			} else if (item instanceof Weapon) {
				l.setActivateAction(new EquipeWeaponAction(game));
			} else if (item instanceof KeyDoor) {
				l.setActivateAction(new UseKey(game));
			}
			return l;
		}

		public static ItemLayout createLoot(Console console, Game game, Item item) {
			ItemLayoutGen l = new ItemLayoutGen(console, item);
			l.setWeaponAction(new LootItemAction(game));

			return l;
		}

		public static ItemLayout createWeaponRack(Console console, Item item) {
			ItemLayoutGen l = new ItemLayoutGen(console, item);

			return l;
		}

	}
}
