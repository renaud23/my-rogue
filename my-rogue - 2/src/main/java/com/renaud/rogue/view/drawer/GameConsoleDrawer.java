package com.renaud.rogue.view.drawer;

import java.awt.Color;

import com.renaud.rogue.view.IDrawOperation;
import com.renaud.rogue.view.console.Console;
import com.renaud.rogue.view.drawer.MainDrawer.Draw;

public class GameConsoleDrawer implements Draw {

	private IDrawOperation op;

	public final static int COLOR_INFO = 0xFFFF00;
	public final static int COLOR_CAREFULL = 0xFF0000;
	public final static int COLOR_SUCCESS = 0x00FFFF;
	public final static int COLOR_INVENTORY = 0x00FF00;

	private int posX;
	private int posY;
	private int largeur;
	private int hauteur;

	private static Console console;

	public GameConsoleDrawer() {}

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

	public static void info(String line) {
		addLine(line, COLOR_INFO);
	}

	public static void success(String line) {
		addLine(line, COLOR_SUCCESS);
	}

	public static void carefull(String line) {
		addLine(line, COLOR_CAREFULL);
	}

	public static void inventory(String line) {
		addLine(line, COLOR_INVENTORY);
	}

	private static void addLine(String line, int color) {
		if (console != null) {
			console.addLigne(line, new Color(color));
		}
	}

}
