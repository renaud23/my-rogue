package com.renaud.rogue.layout.loot.item;

import com.renaud.rogue.game.inventaire.Ammunition;
import com.renaud.rogue.game.inventaire.Item;
import com.renaud.rogue.game.sequence.Game;
import com.renaud.rogue.game.weapon.Weapon;
import com.renaud.rogue.layout.GridLayoutListener;

public interface ItemLayout extends GridLayoutListener<ItemLayout> {

	default void over(ItemLayout u, int i, int j) {};

	public Item getItem();

	public static class Factory {

		public static ItemLayout createInventory(Game game, Item item) {
			ItemLayoutGen l = new ItemLayoutGen(item);
			l.setWeaponAction(new ThrowItemAction(game));
			if (item instanceof Ammunition) {
				l.setActivateAction(new ReloadWeaponAction(game));
			} else if (item instanceof Weapon) {
				l.setActivateAction(new EquipeWeaponAction(game));
			}
			return l;
		}

		public static ItemLayout createLoot(Game game, Item item) {
			ItemLayoutGen l = new ItemLayoutGen(item);
			l.setWeaponAction(new LootItemAction(game));

			return l;
		}

		public static ItemLayout createWeaponRack(Item item) {
			ItemLayoutGen l = new ItemLayoutGen(item);

			return l;
		}

	}
}
