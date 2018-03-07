package com.renaud.ascii.dongeon;

public class Room {

	private int x;
	private int y;
	private int largeur;
	private int hauteur;
	private int centerX;
	private int centerY;

	public Room(int x, int y, int largeur, int hauteur) {
		this.x = x;
		this.y = y;
		this.largeur = largeur;
		this.hauteur = hauteur;
		centerX = x + largeur / 2;
		centerY = y + hauteur / 2;
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public boolean intersec(Room room) {
		int xi = Math.max(x + largeur, room.x + room.largeur);
		xi -= Math.min(x, room.x);

		int yi = Math.max(y + hauteur, room.y + room.hauteur);
		yi -= Math.min(y, room.y);
		if (xi <= (largeur + room.largeur) && yi <= (hauteur + room.hauteur)) {
			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		return "Room [x=" + x + ", y=" + y + ", largeur=" + largeur + ", hauteur=" + hauteur + "]";
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getLargeur() {
		return largeur;
	}

	public int getHauteur() {
		return hauteur;
	}

}
