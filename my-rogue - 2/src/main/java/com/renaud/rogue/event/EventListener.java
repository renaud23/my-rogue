package com.renaud.rogue.event;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class EventListener implements KeyListener, MouseMotionListener {

	public EventListener(KeyboardEvent keyboard) {
		this.keyboard = keyboard;
	}

	KeyboardEvent keyboard;

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				keyboard.keyUpPressed();
				break;
			case KeyEvent.VK_DOWN:
				keyboard.keyDownPressed();
				break;
			case KeyEvent.VK_RIGHT:
				keyboard.keyRightPressed();
				break;
			case KeyEvent.VK_LEFT:
				keyboard.keyLeftPressed();
				break;
			case KeyEvent.VK_SPACE:
				keyboard.rankedWeaponPressed();
				break;
			case KeyEvent.VK_T:
				keyboard.switchWeaponPressed();
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

}
