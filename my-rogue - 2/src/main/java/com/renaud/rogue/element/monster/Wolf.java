package com.renaud.rogue.element.monster;

import com.renaud.rogue.element.comportement.Comportement;
import com.renaud.rogue.element.comportement.HuntPlayer;
import com.renaud.rogue.element.comportement.RandomWalk;
import com.renaud.rogue.game.Game;
import com.renaud.rogue.world.Tile;

public class Wolf extends AbstractMonster {

	private boolean isHuting = false;

	private Comportement walk;
	private Comportement hunt;

	public Wolf(int x, int y) {
		this.x = x;
		this.y = y;
		walk = new RandomWalk(this);
		hunt = new HuntPlayer(this);

		actionsMax = 3;
		depth = 10;
		level = 1;
		life = 10;
		xp = 2;
		meleeDamage = 5;
	}

	@Override
	public void activate(Game game) {
		actions--;
		if (Math.abs(this.x - game.getJoueur().getX()) <= 1 && Math.abs(this.y - game.getJoueur().getY()) <= 1) {
			game.getJoueur().injured(this);
		}
		else
			if (isHuting) {
				hunt.activate(game);
				if (hunt.isFinished()) {
					isHuting = false;
				}
			}
			else {
				if (game.getWorld().canSee(this, game.getJoueur())) {
					isHuting = true;
					hunt.reset();
					activate(game);
				}
				else {
					walk.activate(game);
				}
			}
	}

	@Override
	public Tile getTile() {
		return Tile.Factory.getWolf();
	}

	@Override
	public String getName() {
		return "Wolf";
	}

}
