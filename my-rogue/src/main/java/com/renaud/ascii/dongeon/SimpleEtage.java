package com.renaud.ascii.dongeon;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleEtage {

	public static Etage generer(int largeur, int hauteur) {
		Etage room = new Etage(largeur, hauteur);
		fill(room);
		makeRooms(room, 20);
		return room;
	}

	private static void makeRooms(Etage etage, int count) {
		Random rand = new Random();
		List<Room> rooms = new ArrayList<>();
		Room previous = null;
		int minH = (int) (etage.getHauteur() * 0.05);
		int minL = (int) (etage.getLargeur() * 0.05);
		while (rooms.size() < count) {
			boolean done = false;
			Room room = null;
			while (!done) {
				done = true;
				int largeur = minL + rand.nextInt((int) (etage.getLargeur() * 0.05));
				int hauteur = minH + rand.nextInt((int) (etage.getHauteur() * 0.05));
				int x = 1 + rand.nextInt(etage.getLargeur() - largeur - 1);
				int y = 1 + rand.nextInt(etage.getHauteur() - hauteur - 1);

				room = new Room(x, y, largeur, hauteur);
				// done = !rooms.stream().map(r -> r.intersec(ro)).reduce(false, (acc, curr) -> acc || curr);
				for (Room other : rooms) {
					if (other.intersec(room)) {
						done = false;
					}
				}
			}

			rooms.add(room);
			fillRoom(etage, room);
			if (previous != null) {
				connect(etage, room, previous);
			}
			previous = room;
		}
		etage.setRooms(rooms);
	}

	private static void connect(Etage etage, Room a, Room b) {
		for (int i = Math.min(a.getCenterX(), b.getCenterX()); i <= Math.max(a.getCenterX(), b.getCenterX()); i++) {
			etage.set(i, a.getCenterY(), Tile.FLOOR);
			etage.set(i, a.getCenterY() + 1, Tile.FLOOR);
		}
		for (int j = Math.min(a.getCenterY(), b.getCenterY()); j <= Math.max(a.getCenterY(), b.getCenterY()); j++) {
			etage.set(b.getCenterX(), j, Tile.FLOOR);
			etage.set(b.getCenterX() + 1, j, Tile.FLOOR);
		}
	}

	private static void fillRoom(Etage etage, Room room) {
		for (int i = 0; i < room.getLargeur(); i++) {
			for (int j = 0; j < room.getHauteur(); j++) {
				etage.set(room.getX() + i, room.getY() + j, Tile.FLOOR);
			}
		}
	}

	private static void fill(Etage etage) {
		int taille = etage.getLargeur() * etage.getHauteur();
		for (int i = 0; i < taille; i++) {
			etage.set(i, Tile.WALL);
		}
	}

	public static void print(Etage etage, PrintStream out) {
		int taille = etage.getLargeur() * etage.getHauteur();
		for (int i = 0; i < taille; i++) {
			int val = etage.get(i);
			switch (val) {
				case Tile.WALL:
					out.print("#");
					break;
				case Tile.FLOOR:
					out.print(".");
					break;
				case Tile.PLAYER:
					out.print("O");
					break;
				case Tile.VIEW:
					out.print("+");
					break;
				default:
					out.print(" ");
			}

			if ((i % etage.getLargeur()) == (etage.getLargeur() - 1)) {
				out.println();
			}
		}
	}

	public final static void main(String[] args) {
		Etage etage = SimpleEtage.generer(100, 50);

		SimpleEtage.print(etage, System.out);
	}
}
