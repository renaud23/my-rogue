package com.renaud.rogue.weapon;

import com.renaud.rogue.element.Joueur;
import com.renaud.rogue.element.Living;
import com.renaud.rogue.element.light.Explosion;
import com.renaud.rogue.game.Game;
import com.renaud.rogue.tools.MathTools;
import com.renaud.rogue.tools.Point;
import com.renaud.rogue.world.Tile;

public class Gun implements RankedWeapon {

	private Living user;
	private int damage = 3;
	private int depht = 6;

	public Gun(Living user) {
		this.user = user;
	}

	@Override
	public int getDepht() {
		return depht;
	}

	@Override
	public void shoot(Game game, int aimx, int aimy) {
		for (Point p : MathTools.getSegment(game.getJoueur().getX(), game.getJoueur().getY(), game.getJoueur().getAimx(), game.getJoueur().getAimy())) {
			if (p.x == game.getJoueur().getX() && p.y == game.getJoueur().getY())
				continue;
			Tile tile = game.getWorld().getTile(p.x, p.y);
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
	}

	public int getDamage() {
		return this.damage * this.user.getLevel();
	}

	@Override
	public String getName() {
		return "gun gun gun";
	}

	public Living getUser() {
		return user;
	}

	@Override
	public boolean canAim(Joueur joueur, int x, int y) {
		int dist = MathTools.distance(joueur.getX(), joueur.getY(), x, y);
		return dist < depht * depht;
	}

}
