package com.renaud.ascii.dongeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleLevel {

	private int largeur;
	private int hauteur;
	private int nbRooms = 10;
	private int couloirSize = 1;

	private Level etage;

	public void setNbRooms(int nbRooms) {
		this.nbRooms = nbRooms;
	}

	public void setCouloirSize(int couloirSize) {
		this.couloirSize = couloirSize;
	}

	public SimpleLevel(int largeur, int hauteur) {
		this.largeur = largeur;
		this.hauteur = hauteur;
		etage = new Level(largeur, hauteur);
		etage.fill(Tile.WALL);
	}

	private void makeRooms() {
		Random rand = new Random();
		List<Room> rooms = new ArrayList<>();
		Room previous = null;
		int minH = (int) (etage.getHauteur() * 0.05);
		int minL = (int) (etage.getLargeur() * 0.05);
		while (rooms.size() < nbRooms) {
			boolean done = false;
			Room room = null;
			while (!done) {
				done = true;
				int largeur = minL + rand.nextInt((int) (etage.getLargeur() * 0.05));
				int hauteur = minH + rand.nextInt((int) (etage.getHauteur() * 0.05));
				int x = 1 + rand.nextInt(etage.getLargeur() - largeur - 1);
				int y = 1 + rand.nextInt(etage.getHauteur() - hauteur - 1);

				room = new Room(x, y, largeur, hauteur);
				// done = !rooms.stream().map(r -> r.intersec(ro)).reduce(false, (acc, curr) ->
				// acc || curr);
				for (Room other : rooms) {
					if (other.intersec(room)) {
						done = false;
					}
				}
			}

			rooms.add(room);
			fillRoom(etage, room);
			if (previous != null) {
				connect(room, previous);
			}
			previous = room;
		}
	}

	// public static Point getStartPoint(Etage e) {
	// int x = 0, y = 0;
	// Random rnd = new Random();
	// int value = Tile.WALL;
	// while (value == Tile.WALL) {
	// x = 1 + rnd.nextInt(e.getLargeur() - 1);
	// y = 1 + rnd.nextInt(e.getHauteur() - 1);
	// value = e.get(x, y);
	// }
	//
	// return room;
	// }

	private void connect(Room a, Room b) {
		for (int i = Math.min(a.getCenterX(), b.getCenterX()); i <= Math.max(a.getCenterX(), b.getCenterX()); i++) {
			for (int k = -couloirSize; k < couloirSize; k++) {
				etage.set(i, a.getCenterY() + k, Tile.FLOOR);
			}
		}
		for (int j = Math.min(a.getCenterY(), b.getCenterY()); j <= Math.max(a.getCenterY(), b.getCenterY()); j++) {
			for (int k = -couloirSize; k < couloirSize; k++) {
				etage.set(b.getCenterX(), j + k, Tile.FLOOR);
			}
		}
	}

	private static void fillRoom(Level etage, Room room) {
		for (int i = 0; i < room.getLargeur(); i++) {
			for (int j = 0; j < room.getHauteur(); j++) {
				etage.set(room.getX() + i, room.getY() + j, Tile.FLOOR);
			}
		}
	}

	public void build() {
		makeRooms();
	}

	public static Builder newInstance(int largeur, int hauteur) {
		return new Builder(largeur, hauteur);
	}

	public Level getEtage() {
		return etage;
	}

	public static class Builder {

		private SimpleLevel e;

		private Builder(int largeur, int hauteur) {
			e = new SimpleLevel(largeur, hauteur);
		}

		public Level build() {
			e.build();
			return e.getEtage();
		}

		public Builder setNbRooms(int nbRooms) {
			e.nbRooms = nbRooms;
			return this;
		}

		public Builder setCouloirSize(int couloirSize) {
			e.couloirSize = couloirSize;
			return this;
		}
	}

	public final static void main(String[] args) {
		Level e = SimpleLevel.newInstance(50, 25).setNbRooms(10).setCouloirSize(1).build();
		e.print(System.out);
	}
}
