package com.renaud.rogue.layout.loot.item;

import com.renaud.rogue.game.inventaire.Item;
import com.renaud.rogue.view.drawer.GameConsoleDrawer;

public class ItemLayoutGen implements ItemLayout {

	private Item item;
	private ItemLayoutAction weaponAction;
	private ItemLayoutAction switchWeaponAction;
	private ItemLayoutAction inventaireAction;
	private ItemLayoutAction activateAction;

	public ItemLayoutGen(Item item) {
		this.item = item;
	}

	@Override
	public void weaponAction(ItemLayout u, int i, int j) {
		if (weaponAction != null) {
			weaponAction.doIt(u, i, j);
		}
	}

	@Override
	public void switchWeaponAction(ItemLayout u, int i, int j) {
		if (switchWeaponAction != null) {
			switchWeaponAction.doIt(u, i, j);
		}
	}

	@Override
	public void inventaireAction(ItemLayout u, int i, int j) {
		if (inventaireAction != null) {
			inventaireAction.doIt(u, i, j);
		}
	}

	@Override
	public void activateAction(ItemLayout u, int i, int j) {
		if (activateAction != null) {
			activateAction.doIt(u, i, j);
		}
	}

	public void setWeaponAction(ItemLayoutAction weaponAction) {
		this.weaponAction = weaponAction;
	}

	public void setSwitchWeaponAction(ItemLayoutAction switchWeaponAction) {
		this.switchWeaponAction = switchWeaponAction;
	}

	public void setInventaireAction(ItemLayoutAction inventaireAction) {
		this.inventaireAction = inventaireAction;
	}

	public void setActivateAction(ItemLayoutAction activateAction) {
		this.activateAction = activateAction;
	}

	@Override
	public void over(ItemLayout u, int i, int j) {
		GameConsoleDrawer.addLine(u.getItem().getDesription(), 0x0000FF);
	}

	@Override
	public Item getItem() {
		return item;
	}

}
