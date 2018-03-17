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
	    } else if (distToPlayer <= this.dephtOfFire * this.dephtOfFire) {
		if (game.getWorld().canSee(this, game.getJoueur()) && this.fireBall == null) {
		    int dx = (game.getJoueur().getX() - this.x)
			    / Math.max(1, Math.abs(game.getJoueur().getX() - this.x));
		    int dy = (game.getJoueur().getY() - this.y)
			    / Math.max(1, Math.abs(game.getJoueur().getY() - this.y));
		    int xi = this.x + dx;
		    int yi = this.y + dy;
		    Tile tile = game.getWorld().getTile(xi, yi);
		    if (tile.getElement() != null) {
			// TODO
		    } else {
			this.fireBall = Projectile.Factory.createFireball(xi, yi, game.getJoueur().getX(),
				game.getJoueur().getY());
			game.addProjectile(this.fireBall);
			game.getWorld().getTile(this.x + dx, this.y + dy).setElement(fireBall);
			this.fireBall.activate(game);
		    }
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
    // constructor(x, y) {
    // this.life = 10;
    // this.damage = 10;
    // this.xp = 10;
    // this.tile = null;
    // this.fireBall = null;
    //

    // }
    //
    // activate(world) {
    // if (this.fireBall !== null && this.fireBall.isFinished()) {
    // this.fireBall = null;
    // }
    // //
    // for (let step = 0; step < this.speed; step++) {
    // const distToPlayer = tools.getDistance(this.x, this.y, world.joueur.x,
    // world.joueur.y);
    // if (this.hasSeenPlayer) {
    // if (distToPlayer > this.depht * this.depht) {
    // this.hasSeenPlayer = false;
    // } else if (distToPlayer <= this.dephtOfFire * this.dephtOfFire) {
    // if (world.canSeePlayer(this) && this.fireBall === null) {
    // let dx = (world.joueur.x - this.x) / Math.max(1, Math.abs(world.joueur.x -
    // this.x));
    // let dy = (world.joueur.y - this.y) / Math.max(1, Math.abs(world.joueur.y -
    // this.y));
    // this.fireBall = createFireBall(this.x + dx, this.y + dy, world.joueur.x,
    // world.joueur.y);
    // world.addProjectile(this.fireBall);
    // this.fireBall.activate(world);
    // }
    // } else {
    // if (world.joueur.x !== this.nx || world.joueur.y !== this.ny) {
    // if (world.canSeePlayer(this)) {
    // this.nx = world.joueur.x;
    // this.ny = world.joueur.y;
    // this.goTo.reset(this.nx, this.ny);
    // }
    // }
    // this.goTo.activate(world);
    // }
    // } else {
    // if (distToPlayer <= this.depht * this.depht) {
    // if (world.canSeePlayer(this)) {
    // this.hasSeenPlayer = true;
    // this.nx = world.joueur.x;
    // this.ny = world.joueur.y;
    // this.goTo.reset(this.nx, this.ny);
    // }
    // }
    // }
    // } // for
    // }

}
