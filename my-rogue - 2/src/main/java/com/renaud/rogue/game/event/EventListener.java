package com.renaud.rogue.game.event;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class EventListener implements KeyListener, MouseMotionListener {

	public EventListener(ActionEvent keyboard) {
		this.keyboard = keyboard;
	}

	ActionEvent keyboard;

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				keyboard.goUpAction();
				break;
			case KeyEvent.VK_DOWN:
				keyboard.goDownAction();
				break;
			case KeyEvent.VK_RIGHT:
				keyboard.goRightAction();
				break;
			case KeyEvent.VK_LEFT:
				keyboard.goLeftAction();
				break;
			case KeyEvent.VK_SPACE:
				keyboard.weaponAction();
				break;
			case KeyEvent.VK_T:
				keyboard.switchWeaponAction();
				break;
			case KeyEvent.VK_E:
				keyboard.useAction();
				break;
			case KeyEvent.VK_I:
				keyboard.inventaireAction();
				break;
			case KeyEvent.VK_A:
				keyboard.activateAction();
				break;
			case KeyEvent.VK_ESCAPE:
				keyboard.annulerAction();
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
