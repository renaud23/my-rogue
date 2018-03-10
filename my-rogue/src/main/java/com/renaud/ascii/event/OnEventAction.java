package com.renaud.ascii.event;

public interface OnEventAction {

	void keyUpPressed();

	void keyUpReleased();

	void keyDownPressed();

	void keyDownReleased();

	void keyLeftPressed();

	void keyLeftReleaseded();

	void keyRightPressed();

	void keyRightRealesed();

	void spacePressed();

	void spaceReleaseded();

	void mouseMoved(int x, int y, int varx, int vary);

}
