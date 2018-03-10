package com.renaud.ascii.view.drawer;

import java.awt.Color;

import com.renaud.ascii.dongeon.Tile;
import com.renaud.ascii.view.DrawOperationAware;
import com.renaud.ascii.view.IDrawOperation;
import com.renaud.ascii.view.IDrawable;
import com.renaud.ascii.view.JImageBuffer;
import com.renaud.ascii.world.World;

public class WorldMapDrawer implements IDrawable, DrawOperationAware {
	private World world;
	private int screenX;
	private int screenY;
	private int screenLargeur;
	private int screenHauteur;

	private int carrHeight;
	private int carrWidth;

	private IDrawOperation op;
	private IDrawOperation buffer;

	public WorldMapDrawer(World world, int screenX, int screenY, int screenLargeur, int screenHauteur) {
		this.screenLargeur = screenLargeur;
		this.screenHauteur = screenHauteur;
		this.world = world;
		this.screenX = screenX;
		this.screenY = screenY;

		carrWidth = Math.max(screenLargeur / world.getLargeur(), 1);
		carrHeight = Math.max(screenHauteur / world.getHauteur(), 1);

		buffer = new JImageBuffer(Color.white, screenLargeur, screenHauteur);
		init();
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

	public void init() {
		buffer.transparentClean();
		for (int i = 0; i < world.getLargeur(); i++) {
			for (int j = 0; j < world.getHauteur(); j++) {
				int value = world.getTile(i, j);
				Color col = Color.BLACK;

				if (value == Tile.WALL) {
					col = Color.red;
				}
				buffer.fillRect(col, i * carrWidth, j * carrHeight, carrWidth, carrHeight, 1.0f);
			}

		}
	}

	@Override
	public void draw() {

		this.op.drawImage(buffer.getImage(), screenX, screenY, 0, 0, 0, 1.0, 1.0f);
	};

}
