package com.renaud.rogue.game.weapon;

import com.renaud.rogue.game.element.Joueur;
import com.renaud.rogue.game.element.Living;
import com.renaud.rogue.game.element.TileElement;
import com.renaud.rogue.game.element.light.Explosion;
import com.renaud.rogue.game.inventaire.Ammunition;
import com.renaud.rogue.game.inventaire.Item;
import com.renaud.rogue.game.tools.MathTools;
import com.renaud.rogue.game.tools.Point;
import com.renaud.rogue.game.world.Game;
import com.renaud.rogue.game.world.TileDungeon;
import com.renaud.rogue.view.drawer.GameConsoleDrawer;

public class BerettaM9 implements RankedWeapon, Item {

	private final static int CHARGER_SIZE = 8;

	private Living user;
	private int damage = 8;
	private int depht = 6;
	private int ammo;

	private TileElement tile = TileElement.Factory.createGun();

	public BerettaM9() {
		ammo = 0;
	}

	public BerettaM9(Living user) {
		this.user = user;
	}

	@Override
	public int getDepht() {
		return depht;
	}

	@Override
	public void shoot(Game game, int aimx, int aimy) {
		if (ammo > 0) {
			ammo--;
			for (Point p : MathTools.getSegment(game.getJoueur().getX(), game.getJoueur().getY(), game.getJoueur().getAimx(), game.getJoueur().getAimy())) {
				if (p.x == game.getJoueur().getX() && p.y == game.getJoueur().getY())
					continue;
				TileDungeon tile = game.getWorld().getTile(p.x, p.y);
				if (!tile.isEmpty()) {
					game.addLightSource(new Explosion(p.x, p.y));
					if (tile.getOccupant() instanceof Living) {
						((Living) tile.getOccupant()).injured(game, this);
					}
					break;
				} else if (!game.getWorld().canGo(p.x, p.y)) {
					game.addLightSource(new Explosion(p.x, p.y));
					break;
				}
			}
		} else {
			GameConsoleDrawer.info("Plus de munition, recharger !");
		}
	}

	public int getDamage() {
		return this.damage * this.user.getLevel() * this.getLevel();
	}

	@Override
	public String getName() {
		return "Beretta M9";
	}

	public Living getUser() {
		return user;
	}

	@Override
	public boolean canAim(Joueur joueur, int x, int y) {
		int dist = MathTools.distance(joueur.getX(), joueur.getY(), x, y);
		return dist < depht * depht;
	}

	public TileElement getTile() {
		return tile;
	}

	public void setUser(Living user) {
		this.user = user;
	}

	@Override
	public String getDesription() {
		return "Berreta 9M : " + ammo + " sur " + CHARGER_SIZE;
	}

	@Override
	public void reload(Ammunition charger) {
		if (charger.getType() == Ammunition.BULLET_9MM) {
			int need = CHARGER_SIZE - this.ammo;
			int can = Math.min(charger.getAmmoLeft(), need);
			this.ammo += can;
			charger.removeAmmo(can);
		} else {
			GameConsoleDrawer.info("Munition incompatible");
		}
	}

	@Override
	public boolean isEmpty() {
		return ammo == 0;
	}

	@Override
	public int getRemainingAmmos() {
		return ammo;
	}

}
