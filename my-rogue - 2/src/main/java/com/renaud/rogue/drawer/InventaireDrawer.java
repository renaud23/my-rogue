package com.renaud.rogue.drawer;

import com.renaud.rogue.drawer.MainDrawer.Draw;
import com.renaud.rogue.element.Joueur;
import com.renaud.rogue.view.IDrawOperation;

public class InventaireDrawer implements Draw {

	private Joueur joueur;
	private int screenLargeur;
	private int screenHauteur;

	public InventaireDrawer(Joueur joueur, int screenLargeur, int screenHauteur) {
		this.joueur = joueur;
		this.screenLargeur = screenLargeur;
		this.screenHauteur = screenHauteur;
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDrawOperation(IDrawOperation op) {
		// TODO Auto-generated method stub

	}

}
