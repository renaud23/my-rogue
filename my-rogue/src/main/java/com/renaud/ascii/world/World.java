package com.renaud.ascii.world;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.renaud.ascii.dongeon.Level;
import com.renaud.ascii.dongeon.Tile;
import com.renaud.ascii.element.Element;
import com.renaud.ascii.element.Joueur;
import com.renaud.ascii.figure.Point;
import com.renaud.ascii.figure.Segment;
import com.renaud.ascii.monster.element.Monster;
import com.renaud.ascii.tools.MathTools;
import com.renaud.ascii.weapon.Shoot;

public class World {

	// private boolean playerStepFinished = false;

	private Level level;
	private Joueur joueur;
	private List<Monster> monsters = new ArrayList<>();
	private List<Shoot> shoots = new ArrayList<>();

	// private PlayerActionGestionnaire mouvements;

	public World() {
	}

	public World(Level level, Joueur joueur) {
		this.level = level;
		this.joueur = joueur;
		Point start = level.peekRandomOne(Tile.FLOOR);
		joueur.setX(start.getX());
		joueur.setY(start.getY());
		// mouvements = new PlayerActionGestionnaire(this);
	}

	public void setTile(int i, int j, int value) {
		level.set(i, j, value);
	}

	public void addElement(Monster m) {
		this.monsters.add(m);
	}

	public void addShoot(Shoot s) {
		this.shoots.add(s);
	}

	public List<Shoot> getShoot() {
		return shoots;
	}

	public Iterable<Monster> getMonsters() {
		return monsters;
	}

	public Iterable<Monster> getVisiblesMonstersByPlayer() {
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

	public boolean canGo(Element e, int x, int y) {
		if (x < 0 || y < 0 || x >= level.getLargeur() || y >= level.getHauteur()) {
			return false;
		}
		if (level.getTile(x, y) != Tile.FLOOR) {
			return false;
		}
		if (joueur.isIn(x, y) && !e.isJoueur())
			return false;
		for (Monster m : monsters) {
			if (m.isIn(x, y)) {
				return false;
			}
		}
		return true;
	}

	public boolean canSeeThrough(Element e, int x, int y) {
		if (x < 0 || y < 0 || x >= level.getLargeur() || y >= level.getHauteur()) {
			return false;
		}
		if (level.getTile(x, y) != Tile.FLOOR) {
			return false;
		}
		for (Monster m : monsters) {
			if (e == m) {
				continue;
			}
			if (m.isIn(x, y) && m.isOpaque()) {
				return false;
			}
		}
		return true;
	}

	public boolean canGo(Element e, int x1, int y1, int x2, int y2) {
		if (!canGo(e, x2, y2))
			return false;
		Segment seg = new Segment(new Point(x1, y1), new Point(x2, y2));

		for (Point w : seg.getPoints()) {
			if (!e.isIn(w.getX(), w.getY())) {
				if (!canGo(e, w.getX(), w.getY())) {
					return false;
				}
			}
		}

		return true;
	}

	public boolean canSeePlayer(Monster m) {
		Point pm = new Point(m.getX(), m.getY());
		Point pj = new Point(joueur.getX(), joueur.getY());

		if (MathTools.distance(pm, pj) <= m.getDepth() * m.getDepth()) {
			Segment seg = new Segment(pm, pj);
			for (Point p : seg.getPoints()) {
				if (!canSeeThrough(m, p.getX(), p.getY())) {
					return false;
				}
			}
			return true;
		}
		return false;
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
