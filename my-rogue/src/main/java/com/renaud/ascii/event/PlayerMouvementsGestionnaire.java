package com.renaud.ascii.event;

import com.renaud.ascii.element.Joueur;
import com.renaud.ascii.world.World;

public class PlayerMouvementsGestionnaire implements OnEventAction {

	private World world;
	private Joueur joueur;

	private boolean goUp = false;
	private boolean goDown = false;
	private boolean goRight = false;
	private boolean goLeft = false;

	public PlayerMouvementsGestionnaire(World world) {
		this.world = world;
		this.joueur = world.getJoueur();
	}

	public boolean activate() {
		boolean u = goUp();
		boolean d = goDown();
		boolean r = goRight();
		boolean l = goLeft();
		return u || d || r || l;
	}

	@Override
	public void mouseMoved(int x, int y, int varx, int vary) {

	}

	private boolean goUp() {
		boolean go = false;
		if (goUp) {
			int next = joueur.getY() - 1;
			if (next >= 0) {
				if (world.canGo(joueur, joueur.getX(), next)) {
					go = true;
				}
			}
		}
		if (go) {
			joueur.goUp();
			return true;
		}
		goUp = false;
		return false;
	}

	private boolean goDown() {
		boolean go = false;
		if (goDown) {
			int next = joueur.getY() + 1;
			if (next < world.getHauteur()) {
				if (world.canGo(joueur, joueur.getX(), next)) {
					go = true;
				}
			}
		}
		if (go) {
			joueur.goDown();
			return true;
		}
		goDown = false;
		return false;
	}

	private boolean goRight() {
		boolean go = false;
		if (goRight) {
			int next = joueur.getX() + 1;
			if (next < world.getLargeur()) {
				if (world.canGo(joueur, next, joueur.getY())) {
					go = true;
				}
			}

		}
		if (go) {
			joueur.goRight();
			return true;
		}
		goRight = false;
		return false;
	}

	private boolean goLeft() {
		boolean go = false;
		if (goLeft) {
			int next = joueur.getX() - 1;
			if (next >= 0) {
				if (world.canGo(joueur, next, joueur.getY())) {
					go = true;
				}
			}

		}
		if (go) {
			joueur.goLeft();
			return true;
		}
		goLeft = false;
		return false;
	}

	@Override
	public void keyUpPressed() {
		goUp = true;
		goDown = false;

	}

	@Override
	public void keyUpReleased() {
		goUp = false;

	}

	@Override
	public void keyDownPressed() {
		goUp = false;
		goDown = true;

	}

	@Override
	public void keyDownReleased() {
		goDown = false;

	}

	@Override
	public void keyLeftPressed() {
		goLeft = true;
		goRight = false;

	}

	@Override
	public void keyLeftReleaseded() {
		goLeft = false;
	}

	@Override
	public void keyRightPressed() {
		goLeft = false;
		goRight = true;

	}

	@Override
	public void keyRightRealesed() {
		goRight = false;
	}

	@Override
	public void spacePressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void spaceReleaseded() {
		// TODO Auto-generated method stub

	}

}
