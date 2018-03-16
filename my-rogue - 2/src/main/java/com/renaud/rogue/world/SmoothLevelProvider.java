package com.renaud.rogue.world;

import java.util.Random;

public class SmoothLevelProvider {

	private Dungeon e;
	private int nbStep;

	private SmoothLevelProvider(int largeur, int hauteur) {
		e = new Dungeon(largeur, hauteur);
		e.fill(Tile.WALL);
	}

	public void setNbStep(int nbStep) {
		this.nbStep = nbStep;
	}

	private void init() {
		Random rnd = new Random();
		for (int i = 1; i < (e.getWidth() - 1); i++) {
			for (int j = 1; j < (e.getHeight() - 1); j++) {
				if (rnd.nextInt(100) > 45) {
					e.setTile(i, j, Tile.Factory.getFloor());
				}
			}
		}
	}

	public void build() {
		init();
		for (int i = 0; i < nbStep; i++) {
			carve();
		}
	}

	private void carve() {
		Dungeon e2 = new Dungeon(e.getWidth(), e.getHeight());
		e2.fill(Tile.WALL);
		for (int i = 2; i < (e.getWidth() - 2); i++) {
			for (int j = 2; j < (e.getHeight() - 2); j++) {
				int nb = 0;

				nb += e.getTile(i - 1, j).getCode() != Tile.WALL ? 0 : 1;
				nb += e.getTile(i + 1, j).getCode() != Tile.WALL ? 0 : 1;
				nb += e.getTile(i, j - 1).getCode() != Tile.WALL ? 0 : 1;
				nb += e.getTile(i, j + 1).getCode() != Tile.WALL ? 0 : 1;
				nb += e.getTile(i - 1, j - 1).getCode() != Tile.WALL ? 0 : 1;
				nb += e.getTile(i + 1, j + 1).getCode() != Tile.WALL ? 0 : 1;
				nb += e.getTile(i - 1, j + 1).getCode() != Tile.WALL ? 0 : 1;
				nb += e.getTile(i + 1, j - 1).getCode() != Tile.WALL ? 0 : 1;
				// http://www.roguebasin.com/index.php?title=Cellular_Automata_Method_for_Generating_Random_Cave-Like_Levels
				if (e.getTile(i, j).getCode() == Tile.WALL) {
					if (nb >= 4) {
						e2.setTile(i, j, Tile.Factory.getWall());
					}
					else
						if (nb < 2) {
							e2.setTile(i, j, Tile.Factory.getFloor());
						}
						else {
							e2.setTile(i, j, Tile.Factory.getFloor());
						}
				}
				else {
					if (nb >= 5) {
						e2.setTile(i, j, Tile.Factory.getWall());
					}
					else {
						e2.setTile(i, j, Tile.Factory.getFloor());
					}
				}
			}
		}
		e = e2;

	}

	public Dungeon getDungeon() {
		return e;
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

		public Builder setNbStep(int nbStep) {
			e.nbStep = nbStep;
			return this;
		}

		public Dungeon build() {
			e.build();
			return e.getDungeon();
		}
	}

	public final static void main(String[] args) {
		Dungeon e = SmoothLevelProvider.newInstance(80, 60).setNbStep(6).build();
		e.print(System.out);
	}
}
