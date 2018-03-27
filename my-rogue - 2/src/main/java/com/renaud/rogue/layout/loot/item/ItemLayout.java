package com.renaud.rogue.layout.loot.item;

import com.renaud.rogue.game.inventaire.Item;
import com.renaud.rogue.game.sequence.Game;
import com.renaud.rogue.layout.GridLayoutListener;

public interface ItemLayout extends GridLayoutListener<ItemLayout> {

	default void over(ItemLayout u, int i, int j) {};

	public Item getItem();

	public static class Factory {

		public static ItemLayout createLootToInventory(Game game, Item item) {
			// if (item instanceof Weapon) {
			// return new ItemLayoutWeapon(item, new LootItemAction(game.getJoueur().getInventaire(), tile, item));
			// }

			return createLoot(game, item);
		}

		public static ItemLayout createInventoryToFloor(Game game, Item item) {
			// if (item instanceof Weapon) {
			// return new ItemLayoutWeapon(item, new LootItemAction(game.getJoueur().getInventaire(), tile, item),
			// new EquipeWeaponAction(game));
			// }

			return createThrow(game, item);
		}

		private static ItemLayout createLoot(Game game, Item item) {
			ItemLayoutGen l = new ItemLayoutGen(item);
			l.setWeaponAction(new LootItemAction(game));

			return l;
		}

		private static ItemLayout createThrow(Game game, Item item) {
			ItemLayoutGen l = new ItemLayoutGen(item);
			l.setWeaponAction(new ThrowItemAction(game));

			return l;
		}

	}
}
