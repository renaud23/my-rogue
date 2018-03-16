package com.renaud.rogue.drawer;

import java.awt.Color;

import com.renaud.rogue.drawer.MainDrawer.Draw;
import com.renaud.rogue.game.GameSequence;
import com.renaud.rogue.tools.Point;
import com.renaud.rogue.view.IDrawOperation;
import com.renaud.rogue.view.JImageBuffer;
import com.renaud.rogue.world.Tile;

public class GameDrawer implements Draw {

	private IDrawOperation op;
	private IDrawOperation buffer;

	private int screenLargeur;
	private int screenHauteur;

	private int largeur;
	private int hauteur;

	private GameSequence game;

	public GameDrawer(GameSequence game, int viewLargeur, int viewHauteur, int screenLargeur, int screenHauteur) {
		this.game = game;
		this.largeur = viewLargeur;
		this.hauteur = viewHauteur;
		this.screenLargeur = screenLargeur;
		this.screenHauteur = screenHauteur;
		buffer = new JImageBuffer(Color.white, screenLargeur, screenHauteur);
	}

	public void draw() {
		buffer.fillRect(Color.RED, 0, 0, screenLargeur, screenHauteur, 1.0f);
		int carrSize = Math.min(screenLargeur / largeur, screenHauteur / hauteur);
		int startX = Math.max(0, game.getJoueur().getX() - largeur / 2);
		startX = Math.min(game.getWorld().getWidth() - largeur, startX);
		int startY = Math.max(0, game.getJoueur().getY() - hauteur / 2);
		startY = Math.min(game.getWorld().getHeight() - hauteur, startY);

		for (int i = 0; i < (largeur * hauteur); i++) {
			int xi = i % largeur;
			int yi = i / largeur;
			int xx = startX + xi;
			int yy = startY + yi;

			Tile tile = game.getJoueur().getMemory(xx, yy);
			StringBuilder bld = new StringBuilder();
			bld.append(tile.getTile());

			buffer.fillRect(Color.black, xi * carrSize, yi * carrSize, carrSize, carrSize, 1.0f);
			buffer.drawChar(bld.toString(), xi * carrSize + carrSize / 2, yi * carrSize + carrSize, carrSize,
					new Color(tile.getColor()));
		}

		for (Point point : game.getJoueur().getVisibilityPoints(game)) {
			int xi = point.getX() - startX;
			int yi = point.getY() - startY;
			Tile tile = game.getWorld().getTile(point.getX(), point.getY());

			StringBuilder bld = new StringBuilder();
			bld.append(tile.getTile());

			buffer.fillRect(Color.black, xi * carrSize, yi * carrSize, carrSize, carrSize, 1.0f);
			buffer.drawChar(bld.toString(), xi * carrSize + carrSize / 2, yi * carrSize + carrSize, carrSize,
					new Color(tile.getColor()));
		}

		drawPlayer();
		// drawMonster(startX, startY, carrSize);

		this.op.drawImage(buffer.getImage(), 0, 0, 0, 0, 0, 1.0, 1.0f);
	}

	// private void drawMonster(int startX, int startY, int carrSize) {
	// for (Monster m : world.getVisiblesMonstersByPlayer()) {
	// if (m.getX() >= startX && m.getX() < startX + largeur && m.getY() >= startY
	// && m.getY() < startY + hauteur) {
	// buffer.fillRect(Color.green, (m.getX() - startX) * carrSize, (m.getY() -
	// startY) * carrSize, carrSize, carrSize, 1.0f);
	// }
	// }
	// }

	private void drawPlayer() {
		int carrSize = Math.min(screenLargeur / largeur, screenHauteur / hauteur);
		int posX = Math.min(game.getJoueur().getX(), largeur / 2);
		posX = Math.max(posX, largeur - game.getWorld().getWidth() + game.getJoueur().getX());
		int posY = Math.min(game.getJoueur().getY(), hauteur / 2);
		posY = Math.max(posY, hauteur - game.getWorld().getHeight() + game.getJoueur().getY());

		StringBuilder bld = new StringBuilder();
		bld.append("O");

		buffer.fillRect(Color.black, posX * carrSize, posY * carrSize, carrSize, carrSize, 1.0f);
		buffer.drawChar(bld.toString(), posX * carrSize + carrSize / 2, posY * carrSize + carrSize, carrSize,
				Color.white);

	}

	public void setDrawOperation(IDrawOperation op) {
		this.op = op;
	}

}
