package com.renaud.rogue.drawer;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.renaud.rogue.drawer.MainDrawer.Draw;
import com.renaud.rogue.element.Element;
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
	    tiles[i] = new Bloc(xi, yi, tileMemory, new Color(tileMemory.getColor()), 0.5f);
	}

	for (Point point : game.getJoueur().getVisibilityPoints(game)) {
	    int xi = point.getX() - startX;
	    int yi = point.getY() - startY;
	    Tile tile = game.getWorld().getTile(point.getX(), point.getY());
	    tiles[xi + yi * largeur] = new Bloc(xi, yi, tile, new Color(tile.getColor()), 1.f);
	    if (!tile.isEmpty()) {
		elements.add(tile.getElement());
	    } else
		for (Projectile proj : game.getProjectiles()) {
		    if (proj.getX() == point.getX() && proj.getY() == point.getY()) {
			projectiles.add(proj);
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
	    buffer.drawChar(bld.toString(), xi * carrSize, yi * carrSize + carrSize, carrSize - 1,
		    new Color(element.getTile().getColor()));
	}

	for (Projectile proj : projectiles) {
	    int xi = proj.getX() - startX;
	    int yi = proj.getY() - startY;
	    StringBuilder bld = new StringBuilder();
	    bld.append(proj.getTile().getTile());
	    buffer.drawChar(bld.toString(), xi * carrSize, yi * carrSize + carrSize / 2, carrSize,
		    new Color(proj.getTile().getColor()));
	}

	if (game.isAiming()) {
	    int xi = game.getJoueur().getAimx() - startX;
	    int yi = game.getJoueur().getAimy() - startY;
	    buffer.fillRect(Color.red, xi * carrSize, yi * carrSize, carrSize, carrSize, 0.5f);
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
