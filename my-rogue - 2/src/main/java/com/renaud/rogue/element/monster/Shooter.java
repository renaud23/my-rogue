package com.renaud.rogue.element.monster;

import com.renaud.rogue.element.Monster;
import com.renaud.rogue.element.comportement.GoTo;
import com.renaud.rogue.element.projectile.Projectile;
import com.renaud.rogue.game.Game;
import com.renaud.rogue.tools.MathTools;
import com.renaud.rogue.world.Tile;

public class Shooter implements Monster {

	public int x;
	public int y;
	public int depht;
	public int dephtOfFire;
	public int life = 10;
	public String name = "Monster";
	public boolean meleeDamage;

	public int actions;
	public int actionsMax = 2;

	private boolean hasSeenPlayer;
	private GoTo goTo;
	private Projectile fireBall;
	private int nx;
	private int ny;

	public Shooter(int x, int y) {
		this.x = x;
		this.y = y;
		this.hasSeenPlayer = false;
		this.goTo = new GoTo(this, x, y);
	}

	@Override
	public void addX(int dx) {
		x += dx;
	}

	@Override
	public void addY(int dy) {
		y += dy;
	}

	@Override
	public void activate(Game game) {
		actions--;
		if (this.fireBall != null && this.fireBall.isEnd()) {
			this.fireBall = null;
		}
		if (this.hasSeenPlayer) {
			int distToPlayer = MathTools.distance(this.x, this.y, game.getJoueur().getX(), game.getJoueur().getY());
			if (!game.getWorld().canSee(this, game.getJoueur())) {
				this.hasSeenPlayer = false;
			} else if (meleeDamage && Math.abs(this.x - game.getJoueur().getX()) <= 1
					&& Math.abs(this.y - game.getJoueur().getY()) <= 1) {
				game.getJoueur().injured(this);
			} else if (distToPlayer <= this.dephtOfFire * this.dephtOfFire) {
				if (game.getWorld().canSee(this, game.getJoueur()) && this.fireBall == null) {
					int dx = (game.getJoueur().getX() - this.x)
							/ Math.max(1, Math.abs(game.getJoueur().getX() - this.x));
					int dy = (game.getJoueur().getY() - this.y)
							/ Math.max(1, Math.abs(game.getJoueur().getY() - this.y));
					int xi = this.x + dx;
					int yi = this.y + dy;

					this.fireBall = Projectile.Factory.createFireball(xi, yi, game.getJoueur().getX(),
							game.getJoueur().getY());
					game.addProjectile(this.fireBall);

				}
			} else {
				if (game.getJoueur().getX() != this.nx || game.getJoueur().getY() != this.ny) {
					if (game.getWorld().canSee(this, game.getJoueur())) {
						this.nx = game.getJoueur().getX();
						this.ny = game.getJoueur().getY();
						this.goTo.reset(this.nx, this.ny);
					}
				}
				this.goTo.activate(game);
			}
		} else {
			if (game.getWorld().canSee(this, game.getJoueur())) {
				this.hasSeenPlayer = true;
				this.nx = game.getJoueur().getX();
				this.ny = game.getJoueur().getY();
				this.goTo.reset(this.nx, this.ny);
			}

		}

	}

	@Override
	public boolean isDead() {
		return life <= 0;
	}

	@Override
	public int getDepht() {
		return depht;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public Tile getTile() {
		return Tile.Factory.getGhoul();
	}

	@Override
	public boolean isOpaque() {
		return false;
	}

	@Override
	public void startTurn() {
		actions = actionsMax;

	}

	@Override
	public boolean turnIsEnd() {
		return actions <= 0;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public void injured(Projectile projectile) {
		System.out.println("projectile-->shooter");

	}

	@Override
	public void injured(Monster projectile) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return name;
	}

}
