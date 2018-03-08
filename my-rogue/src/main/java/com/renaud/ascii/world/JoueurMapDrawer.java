package com.renaud.ascii.world;

import java.awt.Color;

import com.renaud.ascii.dongeon.Tile;
import com.renaud.ascii.element.Joueur;
import com.renaud.ascii.figure.Point;
import com.renaud.ascii.view.DrawOperationAware;
import com.renaud.ascii.view.IDrawOperation;
import com.renaud.ascii.view.IDrawable;
import com.renaud.ascii.view.JImageBuffer;

public class JoueurMapDrawer implements IDrawable, DrawOperationAware {

	private float alpha = 0.8f;
	private World world;
	private Joueur joueur;
	private int screenX;
	private int screenY;
	private int screenLargeur;
	private int screenHauteur;

	private int carrHeight;
	private int carrWidth;

	private IDrawOperation op;
	private IDrawOperation buffer;

	public JoueurMapDrawer(World world, Joueur joueur, int screenX, int screenY, int screenLargeur, int screenHauteur) {
		this.world = world;
		this.screenLargeur = screenLargeur;
		this.screenHauteur = screenHauteur;
		this.joueur = joueur;
		this.screenX = screenX;
		this.screenY = screenY;

		carrWidth = Math.max(screenLargeur / world.getLargeur(), 1);
		carrHeight = Math.max(screenHauteur / world.getHauteur(), 1);

		buffer = new JImageBuffer(Color.white, screenLargeur, screenHauteur);
		buffer.transparentClean();
		buffer.fillRect(Color.BLUE, 0, 0, screenLargeur, screenHauteur, alpha);

	}

	public void setDrawOperation(IDrawOperation op) {
		this.op = op;
	}

	@Override
	public boolean isChange() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setChange() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw() {

		for (Point p : joueur.getLastVisibilityPoints()) {

			int value = world.getTile(p.getX(), p.getY());
			Color col = Color.gray;
			if (value == Tile.WALL) {
				col = Color.red;
			}

			buffer.fillRect(col, p.getX() * carrWidth, p.getY() * carrHeight, carrWidth, carrHeight, 0.5f);
		}

		buffer.fillRect(Color.yellow, joueur.getX() * carrWidth, joueur.getY() * carrHeight, carrWidth, carrHeight,
				1.0f);

		this.op.drawImage(buffer.getImage(), screenX, screenY, 0, 0, 0, 1.0, alpha);
	};

}
