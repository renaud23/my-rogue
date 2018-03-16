package com.renaud.rogue.drawer;

import java.awt.Color;

import com.renaud.rogue.game.GameSequence;
import com.renaud.rogue.tools.Point;
import com.renaud.rogue.view.DrawOperationAware;
import com.renaud.rogue.view.IDrawOperation;
import com.renaud.rogue.view.IDrawable;
import com.renaud.rogue.view.JImageBuffer;
import com.renaud.rogue.world.Tile;

public class MinimapDrawer implements IDrawable, DrawOperationAware {

	private float alpha = 0.8f;
	private GameSequence game;
	private int screenX;
	private int screenY;

	private int carrHeight;
	private int carrWidth;

	private IDrawOperation op;
	private IDrawOperation buffer;

	public MinimapDrawer(GameSequence game, int screenX, int screenY, int screenLargeur, int screenHauteur) {
		this.game = game;
		this.screenX = screenX;
		this.screenY = screenY;

		carrWidth = Math.max(screenLargeur / game.getWorld().getWidth(), 1);
		carrHeight = Math.max(screenHauteur / game.getWorld().getHeight(), 1);

		buffer = new JImageBuffer(Color.white, screenLargeur, screenHauteur);
		buffer.transparentClean();
		buffer.fillRect(Color.blue, 0, 0, screenLargeur, screenHauteur, alpha);

	}

	public void setDrawOperation(IDrawOperation op) {
		this.op = op;
	}

	@Override
	public void draw() {

		for (Point p : game.getJoueur().getLastComputed()) {
			Tile tile = game.getWorld().getTile(p.getX(), p.getY());
			buffer.fillRect(new Color(tile.getColor()), p.getX() * carrWidth, p.getY() * carrHeight, carrWidth,
					carrHeight, 1.0f);
		}

		buffer.fillRect(Color.yellow, game.getJoueur().getX() * carrWidth, game.getJoueur().getY() * carrHeight,
				carrWidth, carrHeight, 1.0f);

		this.op.drawImage(buffer.getImage(), screenX, screenY, 0, 0, 0, 1.0, alpha);
	};

}
