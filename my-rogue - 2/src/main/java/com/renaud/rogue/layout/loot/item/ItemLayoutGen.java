package com.renaud.rogue.layout.loot.item;

import java.awt.Color;

import com.renaud.rogue.game.inventaire.Item;
import com.renaud.rogue.view.console.Console;
import com.renaud.rogue.view.drawer.GameConsoleDrawer;

public class ItemLayoutGen implements ItemLayout {

	private Item item;
	private Console console;

	private ItemLayoutAction weaponAction;
	private ItemLayoutAction switchWeaponAction;
	private ItemLayoutAction inventaireAction;
	private ItemLayoutAction activateAction;

	public ItemLayoutGen(Console console, Item item) {
		this.item = item;
		this.console = console;
	}

	@Override
	public void weaponAction(ItemLayout u, int i, int j) {
		if (ActionContext.getInstance().getWeaponAction() != null) {
			ActionContext.getInstance().getWeaponAction().doIt(u, i, j);
		} else if (weaponAction != null) {
			weaponAction.doIt(u, i, j);
		}
	}

	@Override
	public void switchWeaponAction(ItemLayout u, int i, int j) {
		if (ActionContext.getInstance().getSwitchWeaponAction() != null) {
			ActionContext.getInstance().getSwitchWeaponAction().doIt(u, i, j);
		} else if (switchWeaponAction != null) {
			switchWeaponAction.doIt(u, i, j);
		}
	}

	@Override
	public void inventaireAction(ItemLayout u, int i, int j) {
		if (ActionContext.getInstance().getInventaireAction() != null) {
			ActionContext.getInstance().getInventaireAction().doIt(u, i, j);
		} else if (inventaireAction != null) {
			inventaireAction.doIt(u, i, j);
		}
	}

	@Override
	public void useAction(ItemLayout u, int i, int j) {
		if (ActionContext.getInstance().getActivateAction() != null) {
			ActionContext.getInstance().getActivateAction().doIt(u, i, j);
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

	@Override
	public void annulerAction(ItemLayout u, int i, int j) {
		ActionContext.getInstance().reset();

	}

	@Override
	public void over(ItemLayout u, int i, int j) {
		GameConsoleDrawer.inventory(u.getItem().getDesription());
		console.clear();
		console.addLigne(u.getItem().getDesription(), Color.green);
		console.addLigne(getAction(), Color.yellow);
	}

	public String getAction() {
		StringBuilder bld = new StringBuilder();
		if (activateAction != null) {
			bld.append("(e) ").append(activateAction.getAction()).append("-");
		}
		return bld.toString();
	}

}
