package com.renaud.rogue.layout.loot.item;

import com.renaud.rogue.game.inventaire.Item;

public class ItemLayoutGen implements ItemLayout {

	private ActionContext context;
	private Item item;

	private ItemLayoutAction weaponAction;
	private ItemLayoutAction switchWeaponAction;
	private ItemLayoutAction inventaireAction;
	private ItemLayoutAction activateAction;

	public ItemLayoutGen(ActionContext context, Item item) {
		this.item = item;
		this.context = context;
	}

	@Override
	public void weaponAction(ItemLayout u, int i, int j) {
		if (context.getWeaponAction() != null) {
			context.getWeaponAction().doIt(u, i, j);
		} else if (weaponAction != null) {
			weaponAction.doIt(u, i, j);
		}
	}

	@Override
	public void switchWeaponAction(ItemLayout u, int i, int j) {
		if (context.getSwitchWeaponAction() != null) {
			context.getSwitchWeaponAction().doIt(u, i, j);
		} else if (switchWeaponAction != null) {
			switchWeaponAction.doIt(u, i, j);
		}
	}

	@Override
	public void inventaireAction(ItemLayout u, int i, int j) {
		if (context.getInventaireAction() != null) {
			context.getInventaireAction().doIt(u, i, j);
		} else if (inventaireAction != null) {
			inventaireAction.doIt(u, i, j);
		}
	}

	@Override
	public void activateAction(ItemLayout u, int i, int j) {
		if (context.getActivateAction() != null) {
			context.getActivateAction().doIt(u, i, j);
		} else if (activateAction != null) {
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
	public Item getItem() {
		return item;
	}

}
