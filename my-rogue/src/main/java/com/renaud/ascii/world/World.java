package com.renaud.ascii.world;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.renaud.ascii.dongeon.Level;
import com.renaud.ascii.dongeon.Tile;
import com.renaud.ascii.element.Element;
import com.renaud.ascii.element.Joueur;
import com.renaud.ascii.element.monster.Monster;
import com.renaud.ascii.event.OnEventAction;
import com.renaud.ascii.figure.Point;
import com.renaud.ascii.figure.Segment;

public class World implements OnEventAction {

	private boolean playerStepFinished = false;

	private Level level;
	private Joueur joueur;
	private List<Monster> monsters = new ArrayList<>();

	private MouvementGestionnaire mouvements;

	public World() {}

	public World(Level level, Joueur joueur) {
		this.level = level;
		this.joueur = joueur;
		Point start = level.peekRandomOne(Tile.FLOOR);
		joueur.setX(start.getX());
		joueur.setY(start.getY());
		mouvements = new MouvementGestionnaire(this, joueur);
	}

	public void setTile(int i, int j, int value) {
		level.set(i, j, value);
	}

	public void addElement(Monster m) {
		this.monsters.add(m);
	}

	public void compute() {
		// jeu au tour par tour
		if (playerStepFinished) {
			for (Monster m : monsters) {
				m.activate(this);
			}
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
		mouvements.keyUpPressed();
	}

	@Override
	public void keyRightPressed() {
		mouvements.keyRightPressed();
	}

	@Override
	public void keyDownPressed() {
		mouvements.keyDownPressed();
	}

	@Override
	public void keyLeftPressed() {
		mouvements.keyLeftPressed();
	}
	/* */

	@Override
	public void keyUpReleased() {
		mouvements.keyUpReleased();
	}

	@Override
	public void keyDownReleased() {
		mouvements.keyDownReleased();
	}

	@Override
	public void keyLeftReleaseded() {
		mouvements.keyLeftReleaseded();
	}

	@Override
	public void keyRightRealesed() {
		mouvements.keyRightRealesed();
	}

	public Iterable<Monster> getMonsters() {
		return monsters;
	}

	public Iterable<Monster> getVisiblesMonsters() {
		Set<Monster> vm = new HashSet<>();
		for (Monster m : monsters) {
			Segment seg = new Segment(new Point(joueur.getX(), joueur.getY()), new Point(m.getX(), m.getY()));
			boolean isVisible = true;
			for (Point p : seg.getPoints()) {
				if (level.getTile(p.getX(), p.getY()) == Tile.WALL) {
					isVisible = false;
					break;
				}
			}
			if (isVisible) {
				vm.add(m);
			}
		}
		return vm;
	}

	public boolean canGo(int x, int y) {
		if (x < 0 || y < 0 || x >= level.getLargeur() || y >= level.getHauteur()) {
			return false;
		}
		if (level.getTile(x, y) != Tile.FLOOR) {
			return false;
		}
		for (Monster m : monsters) {
			if (m.isIn(x, y)) {
				return false;
			}
		}
		return true;
	}

	public boolean canGo(Element e, int x1, int y1, int x2, int y2) {
		if (!canGo(x2, y2))
			return false;
		Segment seg = new Segment(new Point(x1, y1), new Point(x2, y2));

		for (Point w : seg.getPoints()) {
			if (!e.isIn(w.getX(), w.getY())) {
				if (!canGo(w.getX(), w.getY())) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public void mouseMoved(int x, int y, int varx, int vary) {
		mouvements.mouseMoved(x, y, varx, vary);
	}

	public int getTile(int i, int j) {
		return level.getTile(i, j);
	}

	public int getTile(int i) {
		return level.getTile(i);
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

	public Level getLevel() {
		return level;
	}

}
