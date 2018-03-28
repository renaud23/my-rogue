package com.renaud.rogue.layout;

public interface GridLayoutListener<U> {

	default void weaponAction(U u, int i, int j) {}

	default void activateAction(U u, int i, int j) {

	}

	default void switchWeaponAction(U u, int i, int j) {

	}

	default void inventaireAction(U u, int i, int j) {

	}

	default void over(U u, int i, int j) {}

	void annulerAction(U u, int i, int j);
}
