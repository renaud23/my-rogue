package com.renaud.rogue.game.element.comportement;

import com.renaud.rogue.game.element.Element;
import com.renaud.rogue.game.element.projectile.Projectile;
import com.renaud.rogue.game.world.Game;

public class ShootFireball implements Comportement {

	private Projectile fireBall;
	private Element element;

	public ShootFireball(Element element) {
		this.element = element;
	}

	@Override
	public void activate(Game game) {
		int dx = (game.getJoueur().getX() - element.getX()) / Math.max(1, Math.abs(game.getJoueur().getX() - element.getX()));
		int dy = (game.getJoueur().getY() - element.getY()) / Math.max(1, Math.abs(game.getJoueur().getY() - element.getY()));
		int xi = element.getX() + dx;
		int yi = element.getY() + dy;

		this.fireBall = Projectile.Factory.createFireball(xi, yi, game.getJoueur().getX(), game.getJoueur().getY());
		game.addProjectile(this.fireBall);
	}

	@Override
	public boolean isFinished() {
		return this.fireBall == null || this.fireBall.isEnd();
	}

}
