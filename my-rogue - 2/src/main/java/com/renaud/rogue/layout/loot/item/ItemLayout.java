package com.renaud.rogue.layout.loot.item;

import com.renaud.rogue.game.inventaire.Ammunition;
import com.renaud.rogue.game.inventaire.Item;
import com.renaud.rogue.game.sequence.Game;
import com.renaud.rogue.layout.GridLayoutListener;

public interface ItemLayout extends GridLayoutListener<ItemLayout> {

	default void over(ItemLayout u, int i, int j) {};

	public Item getItem();

	public static class Factory {

		private static ActionContext context = new ActionContext();

		public static ItemLayout createInventory(Game game, Item item) {
			ItemLayoutGen l = new ItemLayoutGen(context, item);
			l.setWeaponAction(new ThrowItemAction(game));
			if (item instanceof Ammunition) {
				l.setActivateAction(new ReloadWeaponAction(context, game));
			}
			return l;
		}

		public static ItemLayout createLoot(Game game, Item item) {
			ItemLayoutGen l = new ItemLayoutGen(context, item);
			l.setWeaponAction(new LootItemAction(game));
			return l;
		}

	}
}
