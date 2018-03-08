package com.renaud.ascii.dongeon;

public class LabyrintheLevel implements LevelProvider {
	private Labyrinthe laby;
	private int largeur;
	private int hauteur;
	private Level l;

	private LabyrintheLevel(int largeur, int hauteur) {
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.laby = new Labyrinthe(largeur, hauteur);
		l = new Level(largeur, hauteur);
		l.fill(Tile.WALL);
	}

	public void build() {
		laby.genere();
		laby.print(System.out);
		for (int i = 0; i < (largeur * hauteur); i++) {
			l.set(i, laby.getPosition(i) == 1 ? Tile.WALL : Tile.FLOOR);
		}
	}

	public static Builder newInstance(int largeur, int hauteur) {
		return new Builder(largeur, hauteur);
	}

	@Override
	public Level getLevel() {
		return l;
	}

	public static class Builder {
		private LabyrintheLevel e;

		private Builder(int largeur, int hauteur) {
			e = new LabyrintheLevel(largeur, hauteur);
		}

		public Level build() {
			e.build();
			return e.getLevel();
		}
	}

	public final static void main(String[] args) {
		Level e = LabyrintheLevel.newInstance(51, 51).build();
		e.print(System.out);
	}

}
