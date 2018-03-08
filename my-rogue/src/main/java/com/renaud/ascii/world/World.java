package com.renaud.ascii.world;

import java.io.PrintStream;

import com.renaud.ascii.dongeon.Etage;
import com.renaud.ascii.dongeon.SimpleEtage;
import com.renaud.ascii.dongeon.SmoothEtage;
import com.renaud.ascii.element.Joueur;
import com.renaud.ascii.event.OnEventAction;
import com.renaud.ascii.figure.Point;

public class World implements OnEventAction {

	private boolean playerStepFinished = false;

	private int largeur;
	private int hauteur;

	private Etage etage;
	private Joueur joueur;

	private MouvementGestionnaire mouvements;

	public World(int largeur, int hauteur) {
		this.largeur = largeur;
		this.hauteur = hauteur;
		etage = new Etage(largeur, hauteur);
	}

	public World(Joueur joueur, int largeur, int hauteur) {
		this.largeur = largeur;
		this.hauteur = hauteur;

		etage = SmoothEtage.newInstance(largeur, hauteur).setNbStep(8).build();
		Point start = SmoothEtage.getStartPoint(etage);
		joueur.setX(start.getX());
		joueur.setY(start.getY());

		// etage = SimpleEtage.generer(largeur, hauteur);
		// this.joueur = joueur;
		// Room room = etage.getRooms().get(0);
		// joueur.setX(room.getCenterX());
		// joueur.setY(room.getCenterY());

		mouvements = new MouvementGestionnaire(this, joueur);
	}

	public void setTile(int i, int j, int value) {
		etage.set(i, j, value);
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
		return etage.get(i, j);
	}

	public int getTile(int i) {
		return etage.get(i);
	}

	public void print(PrintStream out) {
		SimpleEtage.print(etage, out);
	}

	public int getLargeur() {
		return largeur;
	}

	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}

	public int getHauteur() {
		return hauteur;
	}

	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}

	public Joueur getJoueur() {
		return joueur;
	}

}
