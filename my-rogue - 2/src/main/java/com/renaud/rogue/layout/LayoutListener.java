package com.renaud.rogue.layout;

public interface LayoutListener {

	default void weaponAction(Layout u) {};

	default void useAction(Layout u) {};

	default void over(Layout u) {};

	default void switchWeaponAction(Layout u) {};

	default void inventaireAction(Layout u) {};

	default void annulerAction(Layout u) {};

}
