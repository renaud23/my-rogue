package com.renaud.ascii.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import com.renaud.ascii.event.OnEventAction;

public class EventListener implements KeyListener, MouseMotionListener {

	List<OnEventAction> listeners = new ArrayList<>();
	private int x;
	private int y;

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		for (OnEventAction l : listeners) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					l.keyUpPressed();
					break;
				case KeyEvent.VK_DOWN:
					l.keyDownPressed();
					break;
				case KeyEvent.VK_RIGHT:
					l.keyRightPressed();
					break;
				case KeyEvent.VK_LEFT:
					l.keyLeftPressed();
					break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		for (OnEventAction l : listeners) {

			switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					l.keyUpReleased();
					break;
				case KeyEvent.VK_DOWN:
					l.keyDownReleased();
					break;
				case KeyEvent.VK_RIGHT:
					l.keyRightRealesed();
					break;
				case KeyEvent.VK_LEFT:
					l.keyLeftReleaseded();
					break;
			}
		}
	}

	public void addListener(OnEventAction listener) {
		listeners.add(listener);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int varx = x - e.getX();
		int vary = y - e.getY();
		x = e.getX();
		y = e.getY();
		for (OnEventAction l : listeners) {
			l.mouseMoved(x, y, varx, vary);
		}
	}

}
