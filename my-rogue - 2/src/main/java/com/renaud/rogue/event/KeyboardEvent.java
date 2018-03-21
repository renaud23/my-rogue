package com.renaud.rogue.event;

public interface KeyboardEvent {

	default void keyUpPressed() {};

	default void keyUpReleased() {};

	default void keyDownPressed() {};

	default void keyDownReleased() {};

	default void keyLeftPressed() {};

	default void keyLeftReleaseded() {};

	default void keyRightPressed() {};

	default void keyRightRealesed() {};

	default void rankedWeaponPressed() {};

	default void rankedWeaponReleaseded() {};

	default void switchWeaponPressed() {};

	default void meleeWeaponReleaseded() {};

	default void mouseMoved(int x, int y, int varx, int vary) {};

}
