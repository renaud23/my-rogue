package com.renaud.rogue.game.element.monster;

import com.renaud.rogue.game.element.TileElement;
import com.renaud.rogue.game.element.comportement.Comportement;
import com.renaud.rogue.game.element.comportement.MoveToPlayer;
import com.renaud.rogue.game.world.Game;

public class Bat extends AbstractMonster {

	private final static int BAT_MINXP = 5;

	private Comportement moveToPlayer = new MoveToPlayer(this);
	private TileElement tile = TileElement.Factory.getWolf();

	public Bat(int x, int y, int level) {
		super(x, y, BAT_MINXP * level);

		this.actionsMax = 3;
		this.depht = 10;
		this.level = level;
		this.life = 10;
		this.meleeDamage = 5;
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
		return "Chauve-souris";
	}

}
