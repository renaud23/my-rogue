package com.renaud.rogue.view.drawer;

import java.awt.Color;

import com.renaud.rogue.element.Joueur;
import com.renaud.rogue.view.IDrawOperation;
import com.renaud.rogue.view.JImageBuffer;
import com.renaud.rogue.view.drawer.MainDrawer.Draw;
import com.renaud.rogue.view.drawer.menu.ItemsListLayout;
import com.renaud.rogue.view.drawer.menu.Layout;

public class InventaireDrawer extends Layout implements Draw {

	// private IDrawOperation op;

	private Joueur joueur;
	private int screenLargeur;
	private int screenHauteur;
	private JImageBuffer buffer;

	private IDrawOperation op;
	private ItemsListLayout itemsListLayout;
	private boolean changed = true;

	public InventaireDrawer(Joueur joueur, int screenLargeur, int screenHauteur) {
		super(0, 0, screenLargeur, screenHauteur);
		this.joueur = joueur;
		this.screenLargeur = screenLargeur;
		this.screenHauteur = screenHauteur;

		this.itemsListLayout = new ItemsListLayout(10, 10, 4 + 5 * 20, 4 + 5 * 20);
		buffer = new JImageBuffer(Color.blue, screenLargeur, screenHauteur);

	}

	@Override
	public void draw() {
		if (changed) {
			buffer.fillRect(Color.blue, 0, 0, screenLargeur, screenHauteur, 1.0f);
			changed = false;
			itemsListLayout.draw();
		}
		this.op.drawImage(buffer.getImage(), 0, 0, 0, 0, 0, 1.0, 1.0f);
	}

	@Override
	public void setDrawOperation(IDrawOperation op) {
		this.op = op;
		itemsListLayout.setDrawOperation(buffer);
	}

}
