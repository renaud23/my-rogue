package com.renaud.rogue.view.drawer;

import java.awt.Color;

import com.renaud.rogue.element.Joueur;
import com.renaud.rogue.view.IDrawOperation;
import com.renaud.rogue.view.JImageBuffer;
import com.renaud.rogue.view.drawer.MainDrawer.Draw;
import com.renaud.rogue.view.drawer.tile.HeartEmpty;
import com.renaud.rogue.view.drawer.tile.HeartFull;
import com.renaud.rogue.view.drawer.tile.HeartMid;
import com.renaud.rogue.view.drawer.tile.RogueTile;

public class HudDrawer implements Draw {

	private IDrawOperation op;
	private IDrawOperation buffer;

	private RogueTile heartFull = new HeartFull();
	private RogueTile heartMid = new HeartMid();
	private RogueTile heartEmpty = new HeartEmpty();

	private Joueur joueur;
	MinimapDrawer map;

	private int screenLargeur;
	private int screenHauteur;
	private int posX;
	private int posY;

	public HudDrawer(Joueur joueur, int posX, int posY, int screenLargeur, int screenHauteur) {

		this.joueur = joueur;
		this.screenLargeur = screenLargeur;
		this.screenHauteur = screenHauteur;
		this.posX = posX;
		this.posY = posY;
		buffer = new JImageBuffer(Color.white, screenLargeur, screenHauteur);

	}

	@Override
	public void draw() {
		buffer.fillRect(Color.gray, 0, 0, screenLargeur, screenHauteur, 1.0f);
		drawLife(0, 0);
		this.op.drawImage(buffer.getImage(), posX, posY, screenLargeur, screenHauteur, 0, 1.0, 1.0f);
	}

	private void drawLife(int x, int y) {
		int restant = joueur.getLife() * 100 / joueur.getMaxLife();

		for (int i = 0; i < 10; i++) {
			if (restant >= 10) {
				buffer.drawImage(heartFull.getImage(), x + i * 16, y, 0, 0, 0, 1.0, 1.0f);
			} else if (restant >= 5) {
				buffer.drawImage(heartMid.getImage(), x + i * 16, y, 0, 0, 0, 1.0, 1.0f);
			} else {
				buffer.drawImage(heartEmpty.getImage(), x + i * 16, y, 0, 0, 0, 1.0, 1.0f);
			}
			restant -= 10;
		}

	}

	@Override
	public void setDrawOperation(IDrawOperation op) {
		this.op = op;

	}
}
