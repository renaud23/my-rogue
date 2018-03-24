package com.renaud.rogue.game.element.monster;

import com.renaud.rogue.game.element.TileElement;
import com.renaud.rogue.game.element.comportement.Comportement;
import com.renaud.rogue.game.element.comportement.MoveToJoueur;
import com.renaud.rogue.game.sequence.Game;

public class Wolf extends AbstractMonster {

	private Comportement moveToPlayer = new MoveToJoueur(this);
	private TileElement tile = TileElement.Factory.getWolf();

	public Wolf(int x, int y) {
		this.x = x;
		this.y = y;

		actionsMax = 3;
		depht = 10;
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
		} else if (game.getWorld().canSee(this, game.getJoueur())) {
			moveToPlayer.activate(game);
		} else {

		}
	}

	@Override
	public TileElement getTile() {
		return tile;
	}

	@Override
	public String getName() {
		return "Wolf";
	}

}
