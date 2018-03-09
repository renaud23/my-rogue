package com.renaud.ascii.dongeon;

import java.io.PrintStream;
import java.util.Random;
import java.util.Stack;

import com.renaud.ascii.figure.Point;

public class SmoothLevelProvider implements LevelProvider {

	private int largeur;
	private int hauteur;
	private Level e;

	private int nbStep;
	private PrintStream out;

	private SmoothLevelProvider(int largeur, int hauteur) {
		e = new Level(largeur, hauteur);
		this.largeur = largeur;
		this.hauteur = hauteur;
		e.fill(Tile.WALL);
	}

	public void setOut(PrintStream out) {
		this.out = out;
	}

	public void setNbStep(int nbStep) {
		this.nbStep = nbStep;
	}

	private void init() {
		Random rnd = new Random();
		for (int i = 1; i < (e.getLargeur() - 1); i++) {
			for (int j = 1; j < (e.getHauteur() - 1); j++) {
				if (rnd.nextInt(100) > 45) {
					e.set(i, j, Tile.FLOOR);
				}
			}
		}
	}

	public void build() {
		init();
		print();
		for (int i = 0; i < nbStep; i++) {
			carve();
		}
		truc();
	}

	private void truc() {
		Level e2 = e.clone();
		Point p = null;
		Point previous = null;
		while ((p = e2.peekOne(Tile.FLOOR)) != null) {
			Point center = getCenter(e2, p);
			if (previous != null) {
				// connect(e, center, previous);
			}
			// previous = center;
		}
	}

	private void connect(Level ll, Point a, Point b) {
		int couloirSize = 1;
		for (int i = Math.min(a.getX(), b.getX()); i <= Math.max(a.getX(), b.getX()); i++) {
			for (int k = -couloirSize; k < couloirSize; k++) {
				ll.set(i, a.getY() + k, Tile.FLOOR);
			}
		}
		for (int j = Math.min(a.getY(), b.getY()); j <= Math.max(a.getY(), b.getY()); j++) {
			for (int k = -couloirSize; k < couloirSize; k++) {
				ll.set(b.getX(), j + k, Tile.FLOOR);
			}
		}

	}

	private Point getCenter(Level e2, Point start) {
		Stack<Point> pile = new Stack<>();
		pile.push(start);
		int minx = 99999, miny = 99999, maxx = 0, maxy = 0;
		while (!pile.isEmpty()) {
			Point p = pile.pop();
			minx = Math.min(minx, p.getX());
			maxx = Math.max(maxx, p.getX());
			miny = Math.min(miny, p.getY());
			maxy = Math.max(maxy, p.getY());
			e2.set(p.getX(), p.getY(), Tile.WALL);
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if (i == 0 && j == 0)
						continue;
					if (e2.get(p.getX() + i, p.getY() + j) == Tile.FLOOR) {
						pile.push(new Point(p.getX() + i, p.getY() + j));
					}
				}
			}

		}
		return new Point((maxx - minx) / 2 + minx, (maxy - miny) / 2 + miny);
	}

	private void carve() {
		Level e2 = new Level(e.getLargeur(), e.getHauteur());
		e2.fill(Tile.WALL);
		for (int i = 2; i < (e.getLargeur() - 2); i++) {
			for (int j = 2; j < (e.getHauteur() - 2); j++) {
				int nb = 0;
				// nb += e.get(i, j) != Tile.WALL ? 0 : 1;
				nb += e.get(i - 1, j) != Tile.WALL ? 0 : 1;
				nb += e.get(i + 1, j) != Tile.WALL ? 0 : 1;
				nb += e.get(i, j - 1) != Tile.WALL ? 0 : 1;
				nb += e.get(i, j + 1) != Tile.WALL ? 0 : 1;
				nb += e.get(i - 1, j - 1) != Tile.WALL ? 0 : 1;
				nb += e.get(i + 1, j + 1) != Tile.WALL ? 0 : 1;
				nb += e.get(i - 1, j + 1) != Tile.WALL ? 0 : 1;
				nb += e.get(i + 1, j - 1) != Tile.WALL ? 0 : 1;
				// http://www.roguebasin.com/index.php?title=Cellular_Automata_Method_for_Generating_Random_Cave-Like_Levels
				if (e.get(i, j) == Tile.WALL) {
					if (nb >= 4) {
						e2.set(i, j, Tile.WALL);
					} else if (nb < 2) {
						e2.set(i, j, Tile.FLOOR);
					} else {
						e2.set(i, j, Tile.FLOOR);
					}
				} else {
					if (nb >= 5) {
						e2.set(i, j, Tile.WALL);
					} else {
						e2.set(i, j, Tile.FLOOR);
					}
				}

				// if (e.get(i, j) == Tile.WALL && nb >= 4) {
				// e2.set(i, j, Tile.WALL);
				// } else {
				// e2.set(i, j, Tile.FLOOR);
				// }
			}
		}
		e = e2;
		print();
	}

	private void print() {
		if (out != null) {
			e.print(out);
			out.println();
		}
	}

	@Override
	public Level getLevel() {
		return e;
	}

	public static Point getStartPoint(Level e) {
		int x = 0, y = 0;
		Random rnd = new Random();
		int value = Tile.WALL;
		while (value == Tile.WALL) {
			x = 1 + rnd.nextInt(e.getLargeur() - 1);
			y = 1 + rnd.nextInt(e.getHauteur() - 1);
			value = e.get(x, y);
		}

		return new Point(x, y);
	}

	/* ******************************* */
	public static Builder newInstance(int largeur, int hauteur) {
		return new Builder(largeur, hauteur);
	}

	public static class Builder {

		SmoothLevelProvider e;

		private Builder(int largeur, int hauteur) {
			e = new SmoothLevelProvider(largeur, hauteur);
		}

		public Builder setOut(PrintStream out) {
			e.out = out;
			return this;
		}

		public Builder setNbStep(int nbStep) {
			e.nbStep = nbStep;
			return this;
		}

		public Level build() {
			e.build();
			return e.getLevel();
		}
	}

	public final static void main(String[] args) {
		Level e = SmoothLevelProvider.newInstance(50, 30).setNbStep(6).setOut(System.out).build();
		e.print(System.out);
	}
}
