package com.renaud.rogue.game.world.dungeon;

import java.util.List;

import com.renaud.rogue.game.element.light.TorcheFixe;
import com.renaud.rogue.game.tools.Point;

public class Cave extends AbstractDungeon {

	public Cave(int width, int height) {
		super(width, height);
	}

	public void addTorche(int x, int y) {
		this.lightsources.add(new TorcheFixe(x, y));
	}

	public Cave clone() {
		Cave d = new Cave(width, height);
		for (int i = 0; i < size; i++) {
			d.setTile(i, tiles[i].clone());
		}
		return d;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getSize() {
		return size;
	}

	public void setFloors(List<Point> floors) {
		this.floors = floors;
	}

	public List<Point> getDoors() {
		return doors;
	}

	public void setDoors(List<Point> doors) {
		this.doors = doors;
	}

}
