package com.renaud.ascii.element;

public interface Element {

	public int getX();

	public int getY();

	public boolean isIn(int x, int y);

	public boolean isJoueur();

}
