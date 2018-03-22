package com.renaud.rogue.element.monster;

import com.renaud.rogue.element.TileElement;
import com.renaud.rogue.element.comportement.Comportement;
import com.renaud.rogue.element.comportement.MoveToJoueur;
import com.renaud.rogue.element.comportement.ShootFireball;
import com.renaud.rogue.game.Game;
import com.renaud.rogue.tools.MathTools;

public class Shooter extends AbstractMonster {

	private Comportement moveToPlayer = new MoveToJoueur(this);
	private Comportement fireballShoot = new ShootFireball(this);
	private TileElement tile = TileElement.Factory.getGhoul();

	public int dephtOfFire;

	public String name = "Monster";
	public boolean isMeleeAttaque;

	public Shooter(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void activate(Game game) {
		actions--;
		if (Math.abs(this.x - game.getJoueur().getX()) <= 1 && Math.abs(this.y - game.getJoueur().getY()) <= 1) {
			if (isMeleeAttaque) {
				game.getJoueur().injured(this);
			}
		} else if (game.getWorld().canSee(this, game.getJoueur())) {
			int dist = MathTools.distance(x, y, game.getJoueur().getX(), game.getJoueur().getY());
			if (dist < dephtOfFire * dephtOfFire) {
				if (fireballShoot.isFinished()) {
					fireballShoot.activate(game);
				}
			} else {
				moveToPlayer.activate(game);
			}
		}

	}

	@Override
	public TileElement getTile() {
		return tile;
	}

	@Override
	public String getName() {
		return name;
	}

}
