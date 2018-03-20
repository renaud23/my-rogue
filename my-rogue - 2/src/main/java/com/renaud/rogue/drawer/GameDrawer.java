package com.renaud.rogue.drawer;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.renaud.rogue.drawer.MainDrawer.Draw;
import com.renaud.rogue.element.Element;
import com.renaud.rogue.element.LightSource;
import com.renaud.rogue.element.light.Explosion;
import com.renaud.rogue.element.projectile.Projectile;
import com.renaud.rogue.game.Game;
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
	private int size;

	private Game game;

	public GameDrawer(Game game, int viewLargeur, int viewHauteur, int screenLargeur, int screenHauteur) {
		this.game = game;
		this.largeur = viewLargeur;
		this.hauteur = viewHauteur;
		this.size = largeur * hauteur;
		this.screenLargeur = screenLargeur;
		this.screenHauteur = screenHauteur;
		buffer = new JImageBuffer(Color.white, screenLargeur, screenHauteur);
	}

	public void draw() {
		buffer.fillRect(Color.black, 0, 0, screenLargeur, screenHauteur, 1.0f);
		int carrSize = Math.min(screenLargeur / largeur, screenHauteur / hauteur);
		int startX = Math.max(0, game.getJoueur().getX() - largeur / 2);
		startX = Math.min(game.getWorld().getWidth() - largeur, startX);
		int startY = Math.max(0, game.getJoueur().getY() - hauteur / 2);
		startY = Math.min(game.getWorld().getHeight() - hauteur, startY);

		List<Element> elements = new ArrayList<>();
		List<Projectile> projectiles = new ArrayList<>();
		Bloc[] tiles = new Bloc[size];
		for (int i = 0; i < size; i++) {
			int xi = i % largeur;
			int yi = i / largeur;
			Tile tileMemory = game.getJoueur().getMemory(startX + xi, startY + yi);
			tiles[i] = new Bloc(xi, yi, tileMemory, new Color(tileMemory.getColor()), 0.3f);
		}

		for (Point point : game.getJoueur().getVisibilityPoints(game)) {
			int xi = point.getX() - startX;
			int yi = point.getY() - startY;
			int si = xi + yi * largeur;
			if (si < size) {
				Tile tile = game.getWorld().getTile(point.getX(), point.getY());
				if (tile.isLighted()) {
					tiles[si] = new Bloc(xi, yi, tile, getColor(tile), 1.0f);
					if (!tile.isEmpty()) {
						elements.add(tile.getElement());
					} else {
						for (Projectile proj : game.getProjectiles()) {
							if (proj.getX() == point.getX() && proj.getY() == point.getY()) {
								projectiles.add(proj);
							}
						}
					}
				}
			}
		}

		for (int i = 0; i < tiles.length; i++) {
			Bloc bloc = tiles[i];
			buffer.fillRect(bloc.color, bloc.x * carrSize, bloc.y * carrSize, carrSize, carrSize, bloc.alpha);
		}

		for (Element element : elements) {
			int xi = element.getX() - startX;
			int yi = element.getY() - startY;
			StringBuilder bld = new StringBuilder();
			bld.append(element.getTile().getTile());
			buffer.drawChar(bld.toString(), xi * carrSize + 1, yi * carrSize + carrSize - 2, carrSize + 1, getColor(element.getTile()));
		}

		for (Projectile proj : projectiles) {
			int xi = proj.getX() - startX;
			int yi = proj.getY() - startY;
			StringBuilder bld = new StringBuilder();
			bld.append(proj.getTile().getTile());
			buffer.drawChar(bld.toString(), xi * carrSize, yi * carrSize + carrSize - 2, carrSize + 1, getColor(proj.getTile()));
		}

		for (LightSource ls : game.getLightSources()) {
			if (ls instanceof Explosion) {
				int xi = ((Explosion) ls).getX() - startX;
				int yi = ((Explosion) ls).getY() - startY;
				buffer.fillRect(Color.red, xi * carrSize, yi * carrSize, carrSize, carrSize, 0.5f);

			}
		}

		if (game.isAiming()) {
			int xi = game.getJoueur().getAimx() - startX;
			int yi = game.getJoueur().getAimy() - startY;
			buffer.fillRect(Color.red, xi * carrSize, yi * carrSize, carrSize, carrSize, 0.5f);
		}

		this.op.drawImage(buffer.getImage(), 0, 0, 0, 0, 0, 1.0, 1.0f);

	}

	private Color getColor(Tile tile) {
		float r = (tile.getColor() >> 16) & 0xFF;
		int ri = (int) (r * tile.getLight().pr);
		float g = (tile.getColor() >> 8) & 0xFF;
		int gi = (int) (g * tile.getLight().pg);
		float b = (tile.getColor() >> 0) & 0xFF;
		int bi = (int) (b * tile.getLight().pb);
		b *= tile.getLight().pb;
		return new Color(ri, gi, bi);
	}

	public void setDrawOperation(IDrawOperation op) {
		this.op = op;
	}

	public static class Bloc {

		public int x;
		public int y;
		public Tile tile;
		public Color color;
		public float alpha;

		public Bloc(int x, int y, Tile tile, Color color, float alpha) {
			this.x = x;
			this.y = y;
			this.tile = tile;
			this.color = color;
			this.alpha = alpha;
		}

	}

}
