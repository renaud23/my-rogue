package com.renaud.rogue.view.drawer;

import java.awt.Color;

import com.renaud.rogue.element.Joueur;
import com.renaud.rogue.view.IDrawOperation;
import com.renaud.rogue.view.JImageBuffer;
import com.renaud.rogue.view.console.Console;
import com.renaud.rogue.view.drawer.MainDrawer.Draw;

public class InventaireDrawer implements Draw {

	private IDrawOperation op;
	private IDrawOperation buffer;

	private Joueur joueur;
	private int screenLargeur;
	private int screenHauteur;
	private Console console = new Console(400, 200);

	public InventaireDrawer(Joueur joueur, int screenLargeur, int screenHauteur) {
		this.joueur = joueur;
		this.screenLargeur = screenLargeur;
		this.screenHauteur = screenHauteur;

		buffer = new JImageBuffer(Color.white, screenLargeur, screenHauteur);
	}

	@Override
	public void draw() {
		buffer.fillRect(Color.blue, 0, 0, screenLargeur, screenHauteur, 1.0f);

		buffer.drawImage(console.getImage(), 20, 20, 0, 0, 0, 1.0, 1.0, 1.0f);

		this.op.drawImage(buffer.getImage(), 0, 0, 0, 0, 0, 1.0, 1.0f);
	}

	@Override
	public void setDrawOperation(IDrawOperation op) {
		this.op = op;
	}

}
