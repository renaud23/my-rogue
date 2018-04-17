package com.renaud.rogue.game.event;

public interface ActionEvent {

	default void goUpAction() {};

	default void goDownAction() {};

	default void goLeftAction() {};

	default void goRightAction() {};

	default void weaponAction() {};

	default void switchWeaponAction() {};

	default void inventaireAction() {};

	default void useAction() {};

	default void activateAction() {};

	default void annulerAction() {};

	default void climbStairs() {};

	default void goDownStairs() {};

}
