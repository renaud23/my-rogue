package com.renaud.rogue.game.element.monster;

import com.renaud.rogue.game.element.TileElement;
import com.renaud.rogue.game.element.comportement.Comportement;
import com.renaud.rogue.game.element.comportement.ShootFireball;
import com.renaud.rogue.game.element.comportement.TrackPlayer;
import com.renaud.rogue.game.tools.MathTools;
import com.renaud.rogue.game.world.Game;

public class Shooter extends AbstractMonster {

	private Comportement trackPlayer = new TrackPlayer(this);
	private Comportement fireballShoot = new ShootFireball(this);
	private boolean seenPlayer;
	private TileElement tile = TileElement.Factory.getGhoul();

	public int dephtOfFire;

	public String name = "Monster";
	public boolean isMeleeAttaque;
	public boolean waitAfterShoot;

	public Shooter(int xp) {
		super(xp);
	}

	public Shooter(int x, int y, int xp) {
		super(x, y, xp);
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
				waitAfterShoot = true;
			} else {
				if (!waitAfterShoot) {
					trackPlayer.activate(game);
				}
				waitAfterShoot = false;
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
