package com.renaud.ascii.dongeon;

import java.io.PrintStream;
import java.util.List;

public class Etage {

	private int largeur;
	private int hauteur;
	private int[] map;
	private List<Room> rooms;

	public Etage(int largeur, int hauteur) {
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.map = new int[largeur * hauteur];
		fill(Tile.UNKNOW);
	}

	public void fill(int value) {
		for (int i = 0; i < (largeur * hauteur); i++) {
			map[i] = value;
		}
	}

	public int getLargeur() {
		return largeur;
	}

	public int getHauteur() {
		return hauteur;
	}

	public int get(int i) {
		return map[i];
	}

	public int get(int i, int j) {
		return map[i + j * largeur];
	}

	public void set(int i, int value) {
		map[i] = value;
	}

	public void set(int i, int j, int value) {
		map[i + j * largeur] = value;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	/*
	 * 
	 */
	public void print(PrintStream out) {
		int taille = largeur * hauteur;
		for (int i = 0; i < taille; i++) {
			int val = get(i);
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

			if (i % largeur == (largeur - 1)) {
				out.println();
			}
		}
	}

}
