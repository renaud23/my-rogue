package com.renaud.ascii.dongeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleLevelProvider {

	private int nbRooms = 10;
	private int couloirSize = 1;

	private Level etage;

	public void setNbRooms(int nbRooms) {
		this.nbRooms = nbRooms;
	}

	public void setCouloirSize(int couloirSize) {
		this.couloirSize = couloirSize;
	}

	public SimpleLevelProvider(int largeur, int hauteur) {

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
				int largeur = minL + rand.nextInt((int) (etage.getLargeur() * 0.2));
				int hauteur = minH + rand.nextInt((int) (etage.getHauteur() * 0.2));
				int x = 1 + rand.nextInt(etage.getLargeur() - largeur - 1);
				int y = 1 + rand.nextInt(etage.getHauteur() - hauteur - 1);

				room = new Room(x, y, largeur, hauteur);
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

	private void connect(Room a, Room b) {
		Random rnd = new Random();
		int size = 1 + rnd.nextInt(couloirSize);

		for (int i = Math.min(a.getCenterX(), b.getCenterX()); i <= Math.max(a.getCenterX(), b.getCenterX()); i++) {
			for (int k = 0; k < size; k++) {
				etage.set(i, a.getCenterY() + k - size / 2, Tile.FLOOR);
			}
		}
		for (int j = Math.min(a.getCenterY(), b.getCenterY()); j <= Math.max(a.getCenterY(), b.getCenterY()); j++) {
			for (int k = 0; k < size; k++) {
				etage.set(b.getCenterX() + k - size / 2, j, Tile.FLOOR);
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

		private SimpleLevelProvider e;

		private Builder(int largeur, int hauteur) {
			e = new SimpleLevelProvider(largeur, hauteur);
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
		Level e = SimpleLevelProvider.newInstance(50, 25).setNbRooms(10).setCouloirSize(1).build();
		e.print(System.out);
	}
}
