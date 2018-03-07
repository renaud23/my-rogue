package com.renaud.ascii.world;

import java.awt.Color;

import com.renaud.ascii.dongeon.Tile;
import com.renaud.ascii.view.DrawOperationAware;
import com.renaud.ascii.view.IDrawOperation;
import com.renaud.ascii.view.IDrawable;
import com.renaud.ascii.view.JImageBuffer;

public class WorldViewer implements IDrawable, DrawOperationAware {

	private IDrawOperation op;
	private IDrawOperation buffer;

	private World world;

	private int screenLargeur;
	private int screenHauteur;

	public WorldViewer(World world, int screenLargeur, int screenHauteur) {
		this.world = world;
		this.screenLargeur = screenLargeur;
		this.screenHauteur = screenHauteur;
		buffer = new JImageBuffer(Color.white, screenLargeur, screenHauteur);
	}

	public boolean isChange() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setChange() {
		// TODO Auto-generated method stub

	}

	public void draw() {
		int taille = world.getLargeur() * world.getHauteur();
		int carrSize = Math.min(screenLargeur / world.getLargeur(), screenHauteur / world.getHauteur());
		for (int i = 0; i < taille; i++) {
			int posX = (i % world.getLargeur()) * carrSize;
			int posY = (i / world.getLargeur()) * carrSize;

			Color col = Color.black;

			if (world.getTile(i) == Tile.WALL) {
				col = Color.red;
			}

			buffer.fillRect(col, posX, posY, carrSize, carrSize, 1.0f);
		}

		int posX = world.getJoueur().getX() * carrSize;
		int posY = world.getJoueur().getY() * carrSize;
		buffer.fillCircle(Color.yellow, posX, posY, carrSize / 2, 1.0f);

		this.op.drawImage(buffer.getImage(), 0, 0, 0, 0, 0, 1.0, 1.0f);
	}

	public void setDrawOperation(IDrawOperation op) {
		this.op = op;

	}

}
