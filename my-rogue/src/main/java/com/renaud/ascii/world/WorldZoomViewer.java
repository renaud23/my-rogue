package com.renaud.ascii.world;

import java.awt.Color;

import com.renaud.ascii.dongeon.Level;
import com.renaud.ascii.dongeon.Tile;
import com.renaud.ascii.element.Joueur;
import com.renaud.ascii.figure.Point;
import com.renaud.ascii.view.DrawOperationAware;
import com.renaud.ascii.view.IDrawOperation;
import com.renaud.ascii.view.IDrawable;
import com.renaud.ascii.view.JImageBuffer;

public class WorldZoomViewer implements IDrawable, DrawOperationAware {

	private IDrawOperation op;
	private IDrawOperation buffer;

	private Level level;

	private int screenLargeur;
	private int screenHauteur;

	private Joueur joueur;
	private int largeur;
	private int hauteur;

	public WorldZoomViewer(Level level, Joueur joueur, int largeur, int hauteur, int screenLargeur, int screenHauteur) {
		this.level = level;
		this.joueur = joueur;
		this.largeur = largeur;
		this.hauteur = hauteur;
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
		int carrSize = Math.min(screenLargeur / largeur, screenHauteur / hauteur);

		int startX = Math.max(0, joueur.getX() - largeur / 2);
		startX = Math.min(level.getLargeur() - largeur, startX);
		int startY = Math.max(0, joueur.getY() - hauteur / 2);
		startY = Math.min(level.getHauteur() - hauteur, startY);

		for (int i = 0; i < (largeur * hauteur); i++) {
			int xi = i % largeur;
			int yi = i / largeur;
			int xx = startX + xi;
			int yy = startY + yi;

			int value = level.get(xx, yy);
			Color col = Color.BLACK;

			if (value == Tile.WALL) {
				col = Color.red;
			}
			buffer.fillRect(col, xi * carrSize, yi * carrSize, carrSize, carrSize, 1.0f);
		}

		drawPlayer();

		this.op.drawImage(buffer.getImage(), 0, 0, 0, 0, 0, 1.0, 1.0f);
	}

	private void drawPlayer() {
		int carrSize = Math.min(screenLargeur / largeur, screenHauteur / hauteur);
		int posX = Math.min(joueur.getX(), largeur / 2);
		posX = Math.max(posX, largeur - level.getLargeur() + joueur.getX());
		int posY = Math.min(joueur.getY(), hauteur / 2);
		posY = Math.max(posY, hauteur - level.getHauteur() + joueur.getY());

		for (Point point : joueur.getVisibilityPoints(level)) {
			int px = point.getX() - joueur.getX() + posX;
			int py = point.getY() - joueur.getY() + posY;
			buffer.fillRect(Color.BLUE, px * carrSize, py * carrSize, carrSize, carrSize, 1.0f);
		}
		buffer.fillCircle(Color.yellow, posX * carrSize, posY * carrSize, carrSize / 2, 1.0f);

	}

	public void setDrawOperation(IDrawOperation op) {
		this.op = op;
	}

}
