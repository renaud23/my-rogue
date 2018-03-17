package com.renaud.rogue.element.projectile;

import java.util.List;

import com.renaud.rogue.element.Element;
import com.renaud.rogue.element.Joueur;
import com.renaud.rogue.element.TurnPlay;
import com.renaud.rogue.game.Game;
import com.renaud.rogue.tools.MathTools;
import com.renaud.rogue.tools.Point;
import com.renaud.rogue.world.Tile;

public class Projectile implements Element, TurnPlay {

    private int x;
    private int y;
    private int ciblex;
    private int cibley;
    private boolean finished;

    public int depht;
    public int speed;
    public boolean opaque;
    public Tile tile;

    private int actions;
    private int actionsMax;
    private List<Point> segment;

    public Projectile(int x, int y, int ciblex, int cibley) {
	this.x = x;
	this.y = y;
	this.ciblex = ciblex;
	this.cibley = cibley;
    }

    public void init() {
	double pente = (cibley - y) / Math.max(1, ciblex - x);
	int varx = (ciblex - x) / Math.max(1, Math.abs(ciblex - x));

	int nx = this.x + Math.round(varx * this.depht);
	int ny = (int) (this.y + Math.round(pente * this.depht));
	// TODO optimiser cette merde
	this.segment = MathTools.getSegment(this.x, this.y, nx, ny);
	this.segment.remove(0);
	for (int i = 0; i < this.segment.size(); i++) {
	    if (i > this.depht) {
		segment.remove(this.depht);
	    }
	}
    }

    @Override
    public void startTurn() {
	actions = actionsMax;
    }

    @Override
    public boolean turnIsEnd() {
	return actions <= 0;
    }

    @Override
    public void activate(Game game) {
	actions--;

	game.getWorld().getTile(x, y).setElement(null);
	for (int i = 0; i < this.speed; i++) {
	    if (this.segment.size() > 0) {
		Point p = this.segment.remove(0);

		Tile next = game.getWorld().getTile(p.x, p.y);
		if (next != null) {
		    if (tile != null) {
			if (tile.getElement() != null) {
			    this.finished = true;

			    if (tile.getElement() instanceof Joueur) {
				// element.injure(this.damage);
				// journal.addRow(this.message);
			    }
			} else if (!next.canWalkOn()) {
			    this.finished = true;
			} else {
			    this.x = p.x;
			    this.y = p.y;
			}
		    }
		}
	    } else {
		this.finished = true;
	    }
	}
	if (!finished) {
	    game.getWorld().getTile(this.x, this.y).setElement(this);
	}
    }

    public boolean isEnd() {
	return this.finished;
    }

    /* */

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
	return tile;
    }

    @Override
    public boolean isOpaque() {
	return opaque;
    }

    /* */

    public static class Factory {
	public static Projectile createFireball(int x, int y, int ciblex, int cibley) {
	    Projectile p = new Projectile(x, y, ciblex, cibley);
	    p.actionsMax = 1;
	    p.depht = 8;
	    p.speed = 1;
	    p.opaque = false;
	    p.tile = Tile.Factory.getFireball();
	    p.init();
	    return p;
	}
    }

}
