package com.renaud.ascii.view.drawer;

import java.awt.Color;

import com.renaud.ascii.dongeon.Tile;
import com.renaud.ascii.figure.Point;
import com.renaud.ascii.view.DrawOperationAware;
import com.renaud.ascii.view.IDrawOperation;
import com.renaud.ascii.view.IDrawable;
import com.renaud.ascii.view.JImageBuffer;
import com.renaud.ascii.world.World;

public class WorldZoomViewer implements IDrawable, DrawOperationAware {

	private IDrawOperation op;
	private IDrawOperation buffer;

	private World world;

	private int screenLargeur;
	private int screenHauteur;

	private int largeur;
	private int hauteur;

	public WorldZoomViewer(World world, int screenLargeur, int screenHauteur) {

		this.world = world;
		this.largeur = world.getLargeur();
		this.hauteur = world.getHauteur();
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

		int startX = Math.max(0, world.getJoueur().getX() - largeur / 2);
		startX = Math.min(world.getLevel().getLargeur() - largeur, startX);
		int startY = Math.max(0, world.getJoueur().getY() - hauteur / 2);
		startY = Math.min(world.getLevel().getHauteur() - hauteur, startY);

		for (int i = 0; i < (largeur * hauteur); i++) {
			int xi = i % largeur;
			int yi = i / largeur;
			int xx = startX + xi;
			int yy = startY + yi;

			int value = world.getLevel().getTile(xx, yy);
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
		int posX = Math.min(world.getJoueur().getX(), largeur / 2);
		posX = Math.max(posX, largeur - world.getLevel().getLargeur() + world.getJoueur().getX());
		int posY = Math.min(world.getJoueur().getY(), hauteur / 2);
		posY = Math.max(posY, hauteur - world.getLevel().getHauteur() + world.getJoueur().getY());

		for (Point point : world.getJoueur().getVisibilityPoints(world)) {
			int px = point.getX() - world.getJoueur().getX() + posX;
			int py = point.getY() - world.getJoueur().getY() + posY;
			buffer.fillRect(Color.BLUE, px * carrSize, py * carrSize, carrSize, carrSize, 1.0f);
		}
		buffer.fillCircle(Color.yellow, posX * carrSize, posY * carrSize, carrSize / 2, 1.0f);

	}

	public void setDrawOperation(IDrawOperation op) {
		this.op = op;
	}

}
