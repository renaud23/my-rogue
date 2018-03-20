package com.renaud.rogue.element.light;

import com.renaud.rogue.element.Element;
import com.renaud.rogue.element.LightSource;
import com.renaud.rogue.game.Game;
import com.renaud.rogue.tools.Chrono;
import com.renaud.rogue.world.Tile;

public class Explosion implements LightSource, Element {

	private Chrono timeOfLife;

	private int x;
	private int y;

	boolean start;

	public Explosion(int x, int y) {
		this.x = x;
		this.y = y;

		timeOfLife = new Chrono(800);

	}

	@Override
	public void illumine(Game game) {}

	@Override
	public boolean isEnd() {
		return timeOfLife.isEllapsed();
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setX(int x) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setY(int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public Tile getTile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isOpaque() {
		// TODO Auto-generated method stub
		return false;
	}

}
