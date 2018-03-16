package com.renaud.rogue.element;

import java.util.HashSet;
import java.util.Set;

import com.renaud.rogue.game.GameSequence;
import com.renaud.rogue.tools.MathTools;
import com.renaud.rogue.tools.Point;
import com.renaud.rogue.world.Dungeon;
import com.renaud.rogue.world.Tile;
import com.renaud.rogue.world.World;

public class Joueur implements Element {

	private final static Tile tile = Tile.Factory.getPlayer();
	private int depht;
	private int x;
	private int y;
	private Set<Point> lastComputed = new HashSet<>();

	public Joueur(int x, int y, int worldWidth, int worldHeight) {
		this.x = x;
		this.y = y;
		this.depht = 6;
		this.memory = new Dungeon(worldWidth, worldHeight);
		this.memory.fill(Tile.UNKNOW);
	}

	private Dungeon memory;

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	public Set<Point> getVisibilityPoints(GameSequence game) {

		lastComputed.clear();
		for (int i = -depht; i <= depht; i++) {
			for (int j = -depht; j <= depht; j++) {
				int xi = this.x - i;
				int yi = this.y - j;
				if (xi < 0 || yi < 0 || xi >= game.getWorld().getWidth() || yi >= game.getWorld().getHeight()) {
					continue;
				}
				if (MathTools.distance(xi, yi, this.x, this.y) < this.depht * this.depht) {
					boolean visible = true;
					for (Point p : MathTools.getSegment(x, y, xi, yi)) {
						if (p.x == xi && p.y == yi) {
							continue;
						}

						if (!game.getWorld().getTile(p.x, p.y).canSeeThrought()) {
							visible = false;
						}

					}
					if (visible) {
						lastComputed.add(new Point(xi, yi));
						memory.setTile(xi, yi, game.getWorld().getTile(xi, yi));
					}
				}
			}
		}
		return lastComputed;
	}

	public Set<Point> getLastComputed() {
		return lastComputed;
	}

	public Tile getMemory(int x, int y) {
		return memory.getTile(x, y);
	}

	public Dungeon getMemory() {
		return memory;
	}

	public void goUp() {
		y--;
	}

	public void goDown() {
		y++;
	}

	public void goRight() {
		x++;
	}

	public void goLeft() {
		x--;
	}

	@Override
	public int getDepht() {
		return depht;
	}

	@Override
	public Tile getTile() {
		return tile;
	}

	@Override
	public boolean isOpaque() {
		return false;
	}

	public static void main(String[] args) {
		int lar = 30;
		int hau = 20;
		World w = new World(lar, hau);
		Point start = w.peekEmptyPlace();
		Joueur j = new Joueur(start.x, start.y, lar, hau);

		w.print(System.out);
		System.out.println();

		Set<Point> points = j.getVisibilityPoints(new GameSequence(w, j));
		points.forEach(p -> {
			w.setElement(p.x, p.y, new Blank(p.x, p.y));
		});
		w.setElement(start.x, start.y, j);

		w.print(System.out);
	}

}
