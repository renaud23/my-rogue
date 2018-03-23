package com.renaud.rogue.view.drawer;

import java.awt.Color;

import com.renaud.rogue.game.Game;
import com.renaud.rogue.tools.Point;
import com.renaud.rogue.view.DrawOperationAware;
import com.renaud.rogue.view.IDrawOperation;
import com.renaud.rogue.view.IDrawable;
import com.renaud.rogue.view.JImageBuffer;
import com.renaud.rogue.world.Tile;

public class MinimapDrawer implements IDrawable, DrawOperationAware {

	private float alpha = 1.0f;
	private Game game;
	private int screenX;
	private int screenY;

	private int carrHeight;
	private int carrWidth;

	int margex = 0;
	int margey = 0;

	private IDrawOperation op;
	private IDrawOperation buffer;

	public MinimapDrawer(Game game, int screenX, int screenY, int mapLargeur, int mapHauteur) {
		this.game = game;
		this.screenX = screenX;
		this.screenY = screenY;

		carrWidth = Math.max(mapLargeur / game.getWorld().getWidth(), 1);
		carrHeight = Math.max(mapHauteur / game.getWorld().getHeight(), 1);

		margex = Math.max(mapLargeur % game.getWorld().getWidth(), 1) / 2;
		margey = Math.max(mapHauteur % game.getWorld().getHeight(), 1) / 2;

		buffer = new JImageBuffer(Color.white, mapLargeur, mapHauteur);
		buffer.transparentClean();
		buffer.fillRect(Color.blue, 0, 0, mapLargeur, mapHauteur, alpha);

	}

	public void setDrawOperation(IDrawOperation op) {
		this.op = op;
	}

	@Override
	public void draw() {

		for (Point p : game.getJoueur().getLastComputed()) {
			Tile tile = game.getWorld().getTile(p.getX(), p.getY());
			buffer.fillRect(new Color(tile.getColor()), margex + p.getX() * carrWidth, margey + p.getY() * carrHeight, carrWidth, carrHeight, 1.0f);
		}

		buffer.fillRect(Color.yellow, margex + game.getJoueur().getX() * carrWidth, margey + game.getJoueur().getY() * carrHeight, carrWidth, carrHeight, 1.0f);

		this.op.drawImage(buffer.getImage(), screenX, screenY, 0, 0, 0, 1.0, alpha);
	};

}
