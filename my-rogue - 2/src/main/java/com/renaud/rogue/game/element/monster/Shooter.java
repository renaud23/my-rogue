package com.renaud.rogue.game.element.monster;

import com.renaud.rogue.game.element.TileElement;
import com.renaud.rogue.game.element.comportement.Comportement;
import com.renaud.rogue.game.element.comportement.ShootFireball;
import com.renaud.rogue.game.element.comportement.TrackPlayer;
import com.renaud.rogue.game.sequence.Game;
import com.renaud.rogue.game.tools.MathTools;

public class Shooter extends AbstractMonster {

	private Comportement trackPlayer = new TrackPlayer(this);
	private Comportement fireballShoot = new ShootFireball(this);
	private boolean seenPlayer;
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
		if (!seenPlayer) {
			if (game.getWorld().canSee(this, game.getJoueur())) {
				seenPlayer = true;
				activate(game);
			}
		} else {
			int dist = MathTools.distance(x, y, game.getJoueur().getX(), game.getJoueur().getY());
			if (Math.abs(this.x - game.getJoueur().getX()) <= 1 && Math.abs(this.y - game.getJoueur().getY()) <= 1) {
				if (isMeleeAttaque) {
					game.getJoueur().injured(this);
				}
			} else if (dist < dephtOfFire * dephtOfFire && fireballShoot.isFinished() && game.getWorld().canSee(this, game.getJoueur())) {
				fireballShoot.activate(game);
			} else {
				trackPlayer.activate(game);
				if (trackPlayer.isFinished()) {
					trackPlayer.reset();
					seenPlayer = false;
				}
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
