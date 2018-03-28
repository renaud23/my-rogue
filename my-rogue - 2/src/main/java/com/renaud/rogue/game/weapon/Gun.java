package com.renaud.rogue.game.weapon;

import com.renaud.rogue.game.element.Joueur;
import com.renaud.rogue.game.element.Living;
import com.renaud.rogue.game.element.TileElement;
import com.renaud.rogue.game.element.light.Explosion;
import com.renaud.rogue.game.inventaire.Item;
import com.renaud.rogue.game.sequence.Game;
import com.renaud.rogue.game.tools.MathTools;
import com.renaud.rogue.game.tools.Point;
import com.renaud.rogue.game.world.TileDungeon;

public class Gun implements RankedWeapon, Item {

	private Living user;
	private int damage = 3;
	private int depht = 6;

	private TileElement tile = TileElement.Factory.createGun();

	public Gun() {

	}

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
	}

	public int getDamage() {
		return this.damage * this.user.getLevel();
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
		return "Un putain de fucking gun gun gun !";
	}

}
