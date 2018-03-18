package com.renaud.rogue.drawer;

import java.awt.Color;

import com.renaud.rogue.drawer.MainDrawer.Draw;
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

	Bloc[] tiles = new Bloc[size];
	for (int i = 0; i < size; i++) {
	    int xi = i % largeur;
	    int yi = i / largeur;
	    Tile tileMemory = game.getJoueur().getMemory(startX + xi, startY + yi);
	    tiles[i] = new Bloc(xi, yi, tileMemory, new Color(tileMemory.getColor()), 0.5f);
	}

	for (Point point : game.getJoueur().getVisibilityPoints(game)) {
	    int xi = point.getX() - startX;
	    int yi = point.getY() - startY;
	    Tile tile = game.getWorld().getTile(point.getX(), point.getY());
	    tiles[xi + yi * largeur] = new Bloc(xi, yi, tile, new Color(tile.getColor()), 1.f);

	}

	for (int i = 0; i < tiles.length; i++) {
	    Bloc bloc = tiles[i];
	    buffer.fillRect(bloc.color, bloc.x * carrSize, bloc.y * carrSize, carrSize, carrSize, bloc.alpha);
	    if (bloc.tile.getElement() != null) {
		StringBuilder bld = new StringBuilder();
		bld.append(bloc.tile.getElement().getTile().getTile());
		buffer.drawChar(bld.toString(), bloc.x * carrSize, bloc.y * carrSize + carrSize / 2, carrSize,
			new Color(bloc.tile.getElement().getTile().getColor()));

	    }
	}

	for (Projectile proj : game.getProjectiles()) {
	    int xi = proj.getX() - startX;
	    int yi = proj.getY() - startY;
	    StringBuilder bld = new StringBuilder();
	    bld.append(proj.getTile().getTile());
	    buffer.drawChar(bld.toString(), xi * carrSize, yi * carrSize + carrSize / 2, carrSize,
		    new Color(proj.getTile().getColor()));
	}

	this.op.drawImage(buffer.getImage(), 0, 0, 0, 0, 0, 1.0, 1.0f);
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
