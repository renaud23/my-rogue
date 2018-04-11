package com.renaud.rogue.view.drawer;

import java.awt.Color;

import com.renaud.rogue.game.element.Joueur;
import com.renaud.rogue.game.weapon.NoWeapon;
import com.renaud.rogue.game.weapon.RankedWeapon;
import com.renaud.rogue.view.IDrawOperation;
import com.renaud.rogue.view.JImageBuffer;
import com.renaud.rogue.view.drawer.MainDrawer.Draw;
import com.renaud.rogue.view.drawer.tile.HeartEmpty;
import com.renaud.rogue.view.drawer.tile.HeartFull;
import com.renaud.rogue.view.drawer.tile.HeartMid;
import com.renaud.rogue.view.drawer.tile.RogueTile;

public class HudDrawer implements Draw {

	private final static Color CadetBlue = new Color(0x5F9EA0);
	private final static Color DarkOrange = new Color(0xFF8C00);
	private final static Color FireBrick = new Color(0xB22222);
	private final static Color Wheat = new Color(0xF5DEB3);

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
		buffer.fillRect(FireBrick, 0, 0, screenLargeur, screenHauteur, 1.0f);
		drawLife(0, 0);
		drawWeapons(0, 50);
		drawXp(0, 100);
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

	private void drawWeapons(int x, int y) {
		int marge = 10;
		int tileSize = 32;
		int xi = screenLargeur - tileSize * 2 - marge;
		xi /= 2;
		buffer.fillRect(Wheat, marge, y - marge, screenLargeur - marge * 2, tileSize + 2 * marge, 1.0f);
		buffer.fillRect(DarkOrange, x + xi, y, tileSize, tileSize, 1.0f);
		buffer.fillRect(DarkOrange, x + xi + 32 + marge, y, tileSize, tileSize, 1.0f);
		buffer.drawImage(joueur.getMeleeWeapon().getTile().getTileImage().getImage(), x + xi, y, 0, 0, 0, 2.0, 1.0f);
		if (!(joueur.getRankedWeapon() instanceof NoWeapon)) {
			buffer.drawImage(joueur.getRankedWeapon().getTile().getTileImage().getImage(), x + xi + tileSize + marge, y, 0, 0, 0, 2.0, 1.0f);

			RankedWeapon rw = (RankedWeapon) joueur.getRankedWeapon();
			for (int i = 0; i < rw.getRemainingAmmos(); i++) {
				buffer.fillRect(Color.yellow, x + xi + 32 + marge + 2 + 3 * i, y + 2, 2, 2, 1.0f);
			}
		}
		int vx = 0;
		if (joueur.getActiveWeapon() == joueur.getRankedWeapon()) {
			vx += marge + tileSize;
		}
		buffer.drawRect(Color.black, x + xi + vx, y, tileSize + 1, tileSize + 1);
		buffer.drawRect(FireBrick, x + xi + vx - 1, y - 1, tileSize + 1, tileSize + 1);
	}

	private void drawXp(int x, int y) {
		double pct = (double) joueur.getXp() / (double) joueur.getXpForNextLevel();
		int width = (int) (pct * (screenLargeur - 20));
		buffer.fillRect(Color.black, x + 10, y, screenLargeur - 20, 20, 1.0f);
		buffer.fillRect(Color.yellow, x + 10, y, width, 20, 1.0f);
	}

	@Override
	public void setDrawOperation(IDrawOperation op) {
		this.op = op;
	}
}
