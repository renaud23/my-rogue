package com.renaud.rogue.game.element;

import java.util.HashSet;
import java.util.Set;

import com.renaud.rogue.game.element.projectile.Projectile;
import com.renaud.rogue.game.inventaire.Inventory;
import com.renaud.rogue.game.sequence.Game;
import com.renaud.rogue.game.tools.MathTools;
import com.renaud.rogue.game.tools.Point;
import com.renaud.rogue.game.weapon.Knife;
import com.renaud.rogue.game.weapon.NoWeapon;
import com.renaud.rogue.game.weapon.Weapon;
import com.renaud.rogue.game.world.Activable;
import com.renaud.rogue.game.world.TileDungeon;
import com.renaud.rogue.game.world.dungeon.Cave;
import com.renaud.rogue.view.drawer.GameConsoleDrawer;

public class Joueur implements Living {

	private final static TileElement tile = TileElement.Factory.createPlayer();

	private int depht;
	private int x;
	private int y;
	private int aimx;
	private int aimy;

	private Inventory inventaire;

	private Weapon rankedWeapon;
	private Weapon meleeWeapon;
	private Weapon activeWeapon;
	private Set<Point> lastComputed = new HashSet<>();

	// private AimingAction aiming;

	int life = 100;
	int maxLife = 100;
	int xp = 0;
	int level = 1;

	public Joueur(int x, int y, int worldWidth, int worldHeight) {
		this.x = x;
		this.y = y;
		this.depht = 12;
		this.memory = new Cave(worldWidth, worldHeight);
		this.memory.fill(TileDungeon.UNKNOW);
		this.rankedWeapon = new NoWeapon();
		this.meleeWeapon = new Knife(this);
		this.activeWeapon = this.meleeWeapon;

		this.inventaire = new Inventory();
	}

	private Cave memory;

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

	public void activate(Game game) {
		TileDungeon tile = game.getWorld().getTile(aimx, aimy);
		if (tile instanceof Activable) {
			((Activable) tile).activate(game, aimx, aimy);
		} else {
			if (tile.getItems().isEmpty()) {
				// game.setOnLoot(true);
			}
		}
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
						TileDungeon tile = game.getWorld().getTile(xi, yi);
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

	public void resetAiming() {
		this.aimx = x;
		this.aimy = y;
	}

	public Set<Point> getLastComputed() {
		return lastComputed;
	}

	public TileDungeon getMemory(int x, int y) {
		return memory.getTile(x, y);
	}

	public Cave getMemory() {
		return memory;
	}

	public int getDepht() {
		return depht;
	}

	@Override
	public TileElement getTile() {
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
		GameConsoleDrawer.carefull("Vous êtes atteind par " + projectile.name);
		this.life -= projectile.damage;
	}

	public void injured(Monster monster) {
		GameConsoleDrawer.carefull("Vous êtes attaqué par " + monster.getName());
		this.life -= monster.getMeleeDamage();
	}

	@Override
	public void injured(Game game, Weapon weapon) {

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

	public void setAimx(int aimx) {
		this.aimx = aimx;
	}

	public void setAimy(int aimy) {
		this.aimy = aimy;
	}

	public Weapon getActiveWeapon() {
		return activeWeapon;
	}

	// public int getAimingDepht() {
	// return this.aiming.getDepht();
	// }

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getMaxLife() {
		return maxLife;
	}

	public void setMaxLife(int maxLife) {
		this.maxLife = maxLife;
	}

	public Inventory getInventory() {
		return inventaire;
	}

	public Weapon getRankedWeapon() {
		return rankedWeapon;
	}

	public void setRankedWeapon(Weapon rankedWeapon) {
		this.rankedWeapon = rankedWeapon;
	}

	public Weapon getMeleeWeapon() {
		return meleeWeapon;
	}

	public void setMeleeWeapon(Weapon meleeWeapon) {
		this.meleeWeapon = meleeWeapon;
	}

	public void setActiveWeapon(Weapon activeWeapon) {
		this.activeWeapon = activeWeapon;
	}

	// public void setAiming(AimingAction aiming) {
	// this.aiming = aiming;
	// }

}
