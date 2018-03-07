package com.renaud.ascii.world;

import com.renaud.ascii.dongeon.Tile;
import com.renaud.ascii.element.Joueur;
import com.renaud.ascii.event.OnEventAction;

public class MouvementGestionnaire implements OnEventAction {

	private World world;
	private Joueur joueur;

	private boolean goUp = false;
	private boolean goDown = false;
	private boolean goRight = false;
	private boolean goLeft = false;

	public MouvementGestionnaire(World world, Joueur joueur) {
		this.world = world;
		this.joueur = joueur;
	}

	public void activate() {
		goUp();
		goDown();
		goRight();
		goLeft();

	}

	@Override
	public void mouseMoved(int x, int y, int varx, int vary) {
		if (varx > 0) {
			joueur.turnRight();
		}
		else
			if (varx < 0) {
				joueur.turnLeft();
			}
	}

	private void goUp() {
		boolean go = false;
		if (goUp) {
			int next = joueur.getY() - 1;
			if (next >= 0) {
				int tile = world.getTile(joueur.getX(), next);
				if (tile == Tile.FLOOR) {
					go = true;
				}
			}

		}
		if (go) {
			joueur.goUp();
		}
		else {
			goUp = false;
		}
	}

	private void goDown() {
		boolean go = false;
		if (goDown) {
			int next = joueur.getY() + 1;
			if (next < world.getHauteur()) {
				int tile = world.getTile(joueur.getX(), next);
				if (tile == Tile.FLOOR) {
					go = true;
				}
			}

		}
		if (go) {
			joueur.goDown();
		}
		else {
			goDown = false;
		}
	}

	private void goRight() {
		boolean go = false;
		if (goRight) {
			int next = joueur.getX() + 1;
			if (next < world.getLargeur()) {
				int tile = world.getTile(next, joueur.getY());
				if (tile == Tile.FLOOR) {
					go = true;
				}
			}

		}
		if (go) {
			joueur.goRight();
		}
		else {
			goRight = false;
		}
	}

	private void goLeft() {
		boolean go = false;
		if (goLeft) {
			int next = joueur.getX() - 1;
			if (next >= 0) {
				int tile = world.getTile(next, joueur.getY());
				if (tile == Tile.FLOOR) {
					go = true;
				}
			}

		}
		if (go) {
			joueur.goLeft();
		}
		else {
			goLeft = false;
		}
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

}
