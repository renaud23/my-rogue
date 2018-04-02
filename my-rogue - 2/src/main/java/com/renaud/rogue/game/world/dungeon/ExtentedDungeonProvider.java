package com.renaud.rogue.game.world.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.renaud.rogue.game.tools.Point;
import com.renaud.rogue.game.tools.Rectangle;
import com.renaud.rogue.game.world.TileDungeon;

public class ExtentedDungeonProvider {

	private Random rnd = new Random();

	private Facility facility;
	private Cave cave;
	private ExtentedDungeon extented;
	private int largeur;
	private int hauteur;

	private int largeurFacility;
	private int hauteurFacility;

	private int xFacility;;
	private int yFacility;

	private ExtentedDungeonProvider(int largeur, int hauteur) {
		facility = new Facility(largeur, hauteur);
		facility.fill(TileDungeon.WALL);
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.largeurFacility = largeur / 2;
		this.hauteurFacility = hauteur / 2;
		this.xFacility = this.largeur - this.largeurFacility;
		this.xFacility /= 2;
		this.yFacility = this.hauteur - this.hauteurFacility;
		this.yFacility /= 2;
	}

	public void buildCave(int step) {
		this.cave = SmoothDungeonProvider.newInstance(this.largeur, this.hauteur).setNbStep(step).carve().fillSmallest().build();
	}

	public void divideFacility(int step) {
		this.facility = FacilityDungeonProvider.newInstance(this.largeurFacility, this.hauteurFacility).divide(step).build();
	}

	public void carveAcces() {
		int minx = Integer.MAX_VALUE;
		Rectangle west = null;
		for (Rectangle room : facility.getRooms()) {
			if (room.x < minx) {
				minx = room.x;
				west = room;
			}
		}

		int y = this.yFacility + west.y + 1 + rnd.nextInt(west.height - 2);
		Point dest = new Point(this.xFacility, y);
		Point origine = new Point(this.xFacility + west.x, y);
		carve(dest, origine);

		this.extented.setTile(this.xFacility + west.x - 1, y, TileDungeon.Factory.createDoor());
	}

	private void carve(Point a, Point b) {
		int dx = b.x - a.x;
		dx /= dx != 0 ? Math.abs(dx) : 1;
		int dy = b.y - a.y;
		dy /= dy != 0 ? Math.abs(dy) : 1;

		int ax = a.x;
		int ay = a.y;
		int i = 0;
		while (ax != b.x || ay != b.y) {
			this.extented.setTile(ax, ay, TileDungeon.Factory.getFloor());
			if (i++ % 2 == 0) {
				ax += dx;
			} else {
				ay += dy;
			}
		}

	}

	public void combine() {

		for (int i = 0; i < this.largeurFacility; i++) {
			for (int j = 0; j < this.hauteurFacility; j++) {
				cave.setTile(this.xFacility + i, this.yFacility + j, this.facility.getTile(i, j));
			}
		}

		this.extented = new ExtentedDungeon(largeur, hauteur);
		for (int i = 0; i < largeur; i++) {
			for (int j = 0; j < hauteur; j++) {
				this.extented.setTile(i, j, this.cave.getTile(i, j));
			}
		}

		List<Point> floors = new ArrayList<>();
		floors.addAll(cave.getFloors());
		floors.addAll(facility.getFloors());

		extented.setFloors(floors);
	}

	public Dungeon getDungeon() {
		return this.extented;
	}

	/* ******************************* */
	public static Builder newInstance(int largeur, int hauteur) {
		return new Builder(largeur, hauteur);
	}

	public static class Builder {

		ExtentedDungeonProvider e;

		public Builder divideFacility(int step) {
			e.divideFacility(step);
			return this;
		}

		public Builder buildCave(int step) {
			e.buildCave(step);
			return this;
		}

		public Dungeon build() {
			return e.getDungeon();
		}

		public Builder combine() {
			e.combine();
			return this;
		}

		public Builder carveAccess() {
			e.carveAcces();
			return this;
		}

		private Builder(int largeur, int hauteur) {
			e = new ExtentedDungeonProvider(largeur, hauteur);
		}

	}

	/* ** */
	public final static void main(String[] args) {
		Dungeon e = ExtentedDungeonProvider.newInstance(200, 200).buildCave(5).divideFacility(4).combine().carveAccess().build();
		e.print(System.out, false);
	}

}
