package com.renaud.rogue.element;

import java.util.HashSet;
import java.util.Set;

import com.renaud.rogue.element.projectile.Projectile;
import com.renaud.rogue.game.Game;
import com.renaud.rogue.tools.MathTools;
import com.renaud.rogue.tools.Point;
import com.renaud.rogue.weapon.Knife;
import com.renaud.rogue.weapon.Weapon;
import com.renaud.rogue.world.Dungeon;
import com.renaud.rogue.world.Tile;

public class Joueur implements Living {

	private final static Tile tile = Tile.Factory.getPlayer();
	private int depht;
	private int x;
	private int y;
	private int aimx;
	private int aimy;
	private Weapon weapon;
	private Set<Point> lastComputed = new HashSet<>();

	public Joueur(int x, int y, int worldWidth, int worldHeight) {
		this.x = x;
		this.y = y;
		this.depht = 12;
		this.memory = new Dungeon(worldWidth, worldHeight);
		this.memory.fill(Tile.UNKNOW);
		this.weapon = new Knife();
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

	public Set<Point> getVisibilityPoints(Game game) {
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
						Tile tile = game.getWorld().getTile(xi, yi);
						float percent = tile.getLight().pr;
						percent += tile.getLight().pg;
						percent += tile.getLight().pb;
						if (percent > 0.5f)
							memory.setTile(xi, yi, tile);
					}
				}
			}
		}
		return lastComputed;
	}

	public void shoot(Game game) {

	}

	public void resetAim() {
		this.aimx = x;
		this.aimy = y;
	}

	public void aimUp() {
		if (Math.abs(y - aimy + 1) <= weapon.getDepht()) {
			aimy--;
		}
	}

	public void aimDown() {
		if (Math.abs(y - aimy - 1) <= weapon.getDepht()) {
			aimy++;
		}
	}

	public void aimLeft() {
		if (Math.abs(x - aimx + 1) <= weapon.getDepht()) {
			aimx--;
		}
	}

	public void aimRight() {
		if (Math.abs(x - aimx - 1) <= weapon.getDepht()) {
			aimx++;
		}
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

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public void injured(Projectile projectile) {
		System.out.println("joueur fireball");

	}

	public void injured(Monster monster) {
		System.out.println("Player injured by " + monster.getName());

	}

	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}

	public int getAimx() {
		return aimx;
	}

	public int getAimy() {
		return aimy;
	}

}
