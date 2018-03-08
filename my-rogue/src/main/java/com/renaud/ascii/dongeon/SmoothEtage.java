package com.renaud.ascii.dongeon;

import java.io.PrintStream;
import java.util.Random;

import com.renaud.ascii.figure.Point;

public class SmoothEtage {

	private Etage e;

	private int nbStep;
	private PrintStream out;

	private SmoothEtage(int largeur, int hauteur) {
		e = new Etage(largeur, hauteur);
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
				if (rnd.nextBoolean()) {
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
	}

	private void carve() {
		Etage e2 = new Etage(e.getLargeur(), e.getHauteur());
		e2.fill(Tile.WALL);
		for (int i = 2; i < (e.getLargeur() - 2); i++) {
			for (int j = 2; j < (e.getHauteur() - 2); j++) {
				int nb = 0;
				nb += e.get(i, j) != Tile.FLOOR ? 0 : 1;
				nb += e.get(i - 1, j) != Tile.FLOOR ? 0 : 1;
				nb += e.get(i + 1, j) != Tile.FLOOR ? 0 : 1;
				nb += e.get(i, j - 1) != Tile.FLOOR ? 0 : 1;
				nb += e.get(i, j + 1) != Tile.FLOOR ? 0 : 1;
				nb += e.get(i - 1, j - 1) != Tile.FLOOR ? 0 : 1;
				nb += e.get(i + 1, j + 1) != Tile.FLOOR ? 0 : 1;
				nb += e.get(i - 1, j + 1) != Tile.FLOOR ? 0 : 1;
				nb += e.get(i + 1, j - 1) != Tile.FLOOR ? 0 : 1;
				if (nb >= 5) {
					e2.set(i, j, Tile.FLOOR);
				}
				else {
					e2.set(i, j, Tile.WALL);
				}
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

	public Etage getEtage() {
		return e;
	}

	public static Point getStartPoint(Etage e) {
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

		SmoothEtage e;

		private Builder(int largeur, int hauteur) {
			e = new SmoothEtage(largeur, hauteur);
		}

		public Builder setOut(PrintStream out) {
			e.out = out;
			return this;
		}

		public Builder setNbStep(int nbStep) {
			e.nbStep = nbStep;
			return this;
		}

		public Etage build() {
			e.build();
			return e.getEtage();
		}
	}

	public final static void main(String[] args) {
		Etage e = SmoothEtage.newInstance(50, 25).setNbStep(6).setOut(System.out).build();
		e.print(System.out);
	}
}
