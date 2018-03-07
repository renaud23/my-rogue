package com.renaud.ascii.world;

import java.awt.Color;

import com.renaud.ascii.dongeon.Tile;
import com.renaud.ascii.element.Joueur;
import com.renaud.ascii.figure.Point;
import com.renaud.ascii.view.DrawOperationAware;
import com.renaud.ascii.view.IDrawOperation;
import com.renaud.ascii.view.IDrawable;
import com.renaud.ascii.view.JImageBuffer;

public class JoueurViewer implements IDrawable, DrawOperationAware {

	private IDrawOperation op;
	private IDrawOperation buffer;

	private World world;

	private int screenLargeur;
	private int screenHauteur;

	private Joueur joueur;
	private int largeur;
	private int hauteur;

	public JoueurViewer(World world, Joueur joueur, int largeur, int hauteur, int screenLargeur, int screenHauteur) {
		this.world = world;
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
		buffer.fillRect(Color.RED, 0, 0, screenLargeur, screenHauteur, 1.0f);
		int carrSize = Math.min(screenLargeur / largeur, screenHauteur / hauteur);

		int startX = Math.max(0, joueur.getX() - largeur / 2);
		startX = Math.min(world.getLargeur() - largeur, startX);
		int startY = Math.max(0, joueur.getY() - hauteur / 2);
		startY = Math.min(world.getHauteur() - hauteur, startY);

		for (int i = 0; i < (largeur * hauteur); i++) {
			int xi = i % largeur;
			int yi = i / largeur;
			int xx = startX + xi;
			int yy = startY + yi;

			int value = joueur.getMemory(xx, yy);
			Color col = Color.black;
			switch (value) {
				case Tile.WALL:
					col = Color.blue;
					break;
				case Tile.FLOOR:
					col = Color.darkGray;
					break;
			}
			buffer.fillRect(col, xi * carrSize, yi * carrSize, carrSize, carrSize, 1.0f);
		}

		for (Point point : joueur.getVisibilityPoints(world)) {
			int xi = point.getX() - startX;
			int yi = point.getY() - startY;
			int value = world.getTile(point.getX(), point.getY());
			Color col = Color.white;

			switch (value) {
				case Tile.WALL:
					col = Color.cyan;
					break;
				case Tile.FLOOR:
					col = Color.gray;
					break;
			}
			buffer.fillRect(col, xi * carrSize, yi * carrSize, carrSize, carrSize, 1.0f);
		}

		drawPlayer();

		this.op.drawImage(buffer.getImage(), 0, 0, 0, 0, 0, 1.0, 1.0f);
	}

	private void drawPlayer() {
		int carrSize = Math.min(screenLargeur / largeur, screenHauteur / hauteur);
		int posX = Math.min(joueur.getX(), largeur / 2);
		posX = Math.max(posX, largeur - world.getLargeur() + joueur.getX());
		int posY = Math.min(joueur.getY(), hauteur / 2);
		posY = Math.max(posY, hauteur - world.getHauteur() + joueur.getY());

		// for (Point point : joueur.getVisibilityPoints(world)) {
		// int px = point.getX() - joueur.getX() + posX;
		// int py = point.getY() - joueur.getY() + posY;
		// buffer.fillRect(Color.BLUE, px * carrSize, py * carrSize, carrSize, carrSize, 1.0f);
		// }
		buffer.fillCircle(Color.yellow, posX * carrSize, posY * carrSize, carrSize / 2, 1.0f);

	}

	public void setDrawOperation(IDrawOperation op) {
		this.op = op;
	}

}
