package com.renaud.rogue.element;

import java.util.HashSet;
import java.util.Set;

import com.renaud.rogue.element.projectile.Projectile;
import com.renaud.rogue.game.Game;
import com.renaud.rogue.tools.MathTools;
import com.renaud.rogue.tools.Point;
import com.renaud.rogue.weapon.Gun;
import com.renaud.rogue.weapon.Knife;
import com.renaud.rogue.weapon.Weapon;
import com.renaud.rogue.world.Tile;
import com.renaud.rogue.world.dungeon.Dungeon;

public class Joueur implements Living {

	private final static Tile tile = Tile.Factory.getPlayer();
	private int depht;
	private int x;
	private int y;
	private int aimx;
	private int aimy;
	private Weapon rankedWeapon;
	private Weapon meleeWeapon;
	private Weapon activeWeapon;
	private Set<Point> lastComputed = new HashSet<>();

	int life = 100;
	int xp = 0;
	int level = 1;

	public Joueur(int x, int y, int worldWidth, int worldHeight) {
		this.x = x;
		this.y = y;
		this.depht = 12;
		this.memory = new Dungeon(worldWidth, worldHeight);
		this.memory.fill(Tile.UNKNOW);
		this.rankedWeapon = new Gun(this);
		this.meleeWeapon = new Knife(this);
		this.activeWeapon = this.meleeWeapon;
	}

	private Dungeon memory;

	public void switchWeapon() {
		if (activeWeapon == meleeWeapon) {
			activeWeapon = rankedWeapon;
		} else {
			activeWeapon = meleeWeapon;
		}
	}

	public void shoot(Game game) {
		this.activeWeapon.shoot(game, aimx, aimy);
	}

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

	public void resetAim() {
		this.aimx = x;
		this.aimy = y;
	}

	public void aimUp() {
		if (activeWeapon.canAim(this, aimx, aimy - 1)) {
			aimy--;
		}
	}

	public void aimDown() {
		if (activeWeapon.canAim(this, aimx, aimy + 1)) {
			aimy++;
		}
	}

	public void aimLeft() {
		if (activeWeapon.canAim(this, aimx - 1, aimy)) {
			aimx--;
		}
	}

	public void aimRight() {
		if (activeWeapon.canAim(this, aimx + 1, aimy)) {
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
	public void injured(Game game, Projectile projectile) {
		System.out.println("Vous êtes atteind par ...");

	}

	public void injured(Monster monster) {
		System.out.println("Vous êtes attaqué par " + monster.getName());
		this.life -= monster.getMeleeDamage();
	}

	@Override
	public boolean isDead() {
		return life <= 0;
	}

	public int getAimx() {
		return aimx;
	}

	public int getAimy() {
		return aimy;
	}

	public Weapon getActiveWeapon() {
		return activeWeapon;
	}

}
