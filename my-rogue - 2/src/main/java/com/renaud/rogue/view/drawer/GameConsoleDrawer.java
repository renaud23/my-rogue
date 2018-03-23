package com.renaud.rogue.view.drawer;

import java.awt.Color;

import com.renaud.rogue.view.IDrawOperation;
import com.renaud.rogue.view.console.Console;
import com.renaud.rogue.view.drawer.MainDrawer.Draw;

public class GameConsoleDrawer implements Draw {

	private IDrawOperation op;

	private int posX;
	private int posY;
	private int largeur;
	private int hauteur;

	private static Console console;

	public GameConsoleDrawer(int posX, int posY, int largeur, int hauteur) {
		this.posX = posX;
		this.posY = posY;
		this.largeur = largeur;
		this.hauteur = hauteur;
		console = new Console(largeur, hauteur);
	}

	@Override
	public void draw() {
		this.op.drawImage(console.getImage(), posX, posY, 0, 0, 0, 1.0, 1.0f);
	}

	@Override
	public void setDrawOperation(IDrawOperation op) {
		this.op = op;
	}

	public static void addLine(String line, int color) {
		if (console != null) {
			console.addLigne(line, new Color(color));
		}
	}
}
