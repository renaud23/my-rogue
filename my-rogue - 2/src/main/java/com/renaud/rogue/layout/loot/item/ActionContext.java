package com.renaud.rogue.layout.loot.item;

public class ActionContext {

	private ItemLayoutAction weaponAction;
	private ItemLayoutAction switchWeaponAction;
	private ItemLayoutAction inventaireAction;
	private ItemLayoutAction activateAction;

	private ActionContext() {}

	private static ActionContext instance;

	public static ActionContext getInstance() {
		if (instance == null) {
			instance = new ActionContext();
		}
		return instance;
	}

	public void reset() {
		weaponAction = null;
		switchWeaponAction = null;
		inventaireAction = null;
		activateAction = null;
	}

	public ItemLayoutAction getWeaponAction() {
		return weaponAction;
	}

	public void setWeaponAction(ItemLayoutAction weaponAction) {
		this.weaponAction = weaponAction;
	}

	public ItemLayoutAction getSwitchWeaponAction() {
		return switchWeaponAction;
	}

	public void setSwitchWeaponAction(ItemLayoutAction switchWeaponAction) {
		this.switchWeaponAction = switchWeaponAction;
	}

	public ItemLayoutAction getInventaireAction() {
		return inventaireAction;
	}

	public void setInventaireAction(ItemLayoutAction inventaireAction) {
		this.inventaireAction = inventaireAction;
	}

	public ItemLayoutAction getActivateAction() {
		return activateAction;
	}

	public void setActivateAction(ItemLayoutAction activateAction) {
		this.activateAction = activateAction;
	}

}
