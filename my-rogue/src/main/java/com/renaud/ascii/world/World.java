package com.renaud.ascii.world;

import com.renaud.ascii.dongeon.Level;
import com.renaud.ascii.dongeon.Tile;
import com.renaud.ascii.element.Joueur;
import com.renaud.ascii.event.OnEventAction;
import com.renaud.ascii.figure.Point;

public class World implements OnEventAction {

	private boolean playerStepFinished = false;

	private Level level;
	private Joueur joueur;

	private MouvementGestionnaire mouvements;

	public World() {}

	public World(Level level, Joueur joueur) {
		this.level = level;
		Point start = level.peekRandomOne(Tile.FLOOR);
		joueur.setX(start.getX());
		joueur.setY(start.getY());
		mouvements = new MouvementGestionnaire(this, joueur);
	}

	public void setTile(int i, int j, int value) {
		level.set(i, j, value);
	}

	public void compute() {
		// jeu au tour par tour
		if (playerStepFinished) {
			System.out.println("mouvement ennemi");
			// TODO
			playerStepFinished = false;
		}
		else {
			if (mouvements.activate()) {
				playerStepFinished = true;
			}
		}
	}

	@Override
	public void keyUpPressed() {
		if (!playerStepFinished) {
			playerStepFinished = true;
			mouvements.keyUpPressed();
		}
	}

	@Override
	public void keyUpReleased() {
		playerStepFinished = true;
		mouvements.keyUpReleased();
	}

	@Override
	public void keyDownPressed() {
		if (!playerStepFinished) {
			playerStepFinished = true;
			mouvements.keyDownPressed();
		}

	}

	@Override
	public void keyDownReleased() {
		playerStepFinished = true;
		mouvements.keyDownReleased();
	}

	@Override
	public void keyLeftPressed() {
		if (!playerStepFinished) {
			playerStepFinished = true;
			mouvements.keyLeftPressed();
		}
	}

	@Override
	public void keyLeftReleaseded() {
		playerStepFinished = true;
		mouvements.keyLeftReleaseded();
	}

	@Override
	public void keyRightPressed() {
		if (!playerStepFinished) {
			playerStepFinished = true;
			mouvements.keyRightPressed();
		}
	}

	@Override
	public void keyRightRealesed() {
		playerStepFinished = true;
		mouvements.keyRightRealesed();
	}

	@Override
	public void mouseMoved(int x, int y, int varx, int vary) {
		mouvements.mouseMoved(x, y, varx, vary);
	}

	public int getTile(int i, int j) {
		return level.get(i, j);
	}

	public int getTile(int i) {
		return level.get(i);
	}

	public int getLargeur() {
		return level.getLargeur();
	}

	public int getHauteur() {
		return level.getHauteur();
	}

	public Joueur getJoueur() {
		return joueur;
	}

}
