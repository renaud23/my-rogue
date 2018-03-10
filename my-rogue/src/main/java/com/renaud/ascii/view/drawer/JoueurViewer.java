package com.renaud.ascii.view.drawer;

import java.awt.Color;

import com.renaud.ascii.dongeon.Tile;
import com.renaud.ascii.figure.Point;
import com.renaud.ascii.monster.element.Monster;
import com.renaud.ascii.view.DrawOperationAware;
import com.renaud.ascii.view.IDrawOperation;
import com.renaud.ascii.view.IDrawable;
import com.renaud.ascii.view.JImageBuffer;
import com.renaud.ascii.world.World;

public class JoueurViewer implements IDrawable, DrawOperationAware {

	private IDrawOperation op;
	private IDrawOperation buffer;

	private World world;
	private int screenLargeur;
	private int screenHauteur;

	private int largeur;
	private int hauteur;

	public JoueurViewer(World world, int viewLargeur, int viewHauteur, int screenLargeur, int screenHauteur) {
		this.world = world;
		this.largeur = viewLargeur;
		this.hauteur = viewHauteur;
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

		int startX = Math.max(0, world.getJoueur().getX() - largeur / 2);
		startX = Math.min(world.getLevel().getLargeur() - largeur, startX);
		int startY = Math.max(0, world.getJoueur().getY() - hauteur / 2);
		startY = Math.min(world.getLevel().getHauteur() - hauteur, startY);

		for (int i = 0; i < (largeur * hauteur); i++) {
			int xi = i % largeur;
			int yi = i / largeur;
			int xx = startX + xi;
			int yy = startY + yi;

			int value = world.getJoueur().getMemory(xx, yy);
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

		for (Point point : world.getJoueur().getVisibilityPoints(world)) {
			int xi = point.getX() - startX;
			int yi = point.getY() - startY;
			int value = world.getLevel().getTile(point.getX(), point.getY());
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
		drawMonster(startX, startY, carrSize);

		this.op.drawImage(buffer.getImage(), 0, 0, 0, 0, 0, 1.0, 1.0f);
	}

	private void drawMonster(int startX, int startY, int carrSize) {
		for (Monster m : world.getVisiblesMonstersByPlayer()) {
			if (m.getX() >= startX && m.getX() < startX + largeur && m.getY() >= startY
					&& m.getY() < startY + hauteur) {
				buffer.fillRect(Color.green, (m.getX() - startX) * carrSize, (m.getY() - startY) * carrSize, carrSize,
						carrSize, 1.0f);
			}
		}
	}

	private void drawPlayer() {
		int carrSize = Math.min(screenLargeur / largeur, screenHauteur / hauteur);
		int posX = Math.min(world.getJoueur().getX(), largeur / 2);
		posX = Math.max(posX, largeur - world.getLevel().getLargeur() + world.getJoueur().getX());
		int posY = Math.min(world.getJoueur().getY(), hauteur / 2);
		posY = Math.max(posY, hauteur - world.getLevel().getHauteur() + world.getJoueur().getY());

		buffer.fillCircle(Color.yellow, posX * carrSize, posY * carrSize, carrSize / 2, 1.0f);

	}

	public void setDrawOperation(IDrawOperation op) {
		this.op = op;
	}

}
