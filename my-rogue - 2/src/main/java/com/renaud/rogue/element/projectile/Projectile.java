package com.renaud.rogue.element.projectile;

import java.util.List;

import com.renaud.rogue.element.Element;
import com.renaud.rogue.element.Living;
import com.renaud.rogue.element.TurnPlay;
import com.renaud.rogue.element.light.Explosion;
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

	public Projectile(
		int x,
		int y,
		int ciblex,
		int cibley) {
		this.x = x;
		this.y = y;
		this.ciblex = ciblex;
		this.cibley = cibley;
	}

	public void init() {
		this.segment = MathTools.getSegment(x, y, ciblex, cibley);

		// double pente = (cibley - y) / Math.max(1, ciblex - x);
		// int varx = (ciblex - x) / Math.max(1, Math.abs(ciblex - x));
		// int nx = this.x + Math.round(varx * this.depht);
		// int ny = this.y + (int) Math.round(pente * this.depht);
		//
		// this.segment = MathTools.getSegment(this.x, this.y, nx, ny);
		// this.segment.remove(0);
		// if (this.segment.size() > depht) {
		// List<Point> tt = new ArrayList<>();
		// for (int i = 0; i < depht; i++) {
		// tt.add(segment.get(i));
		// }
		// segment = tt;
		// }
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
		if (segment.size() > 0) {
			Point next = segment.remove(0);
			if (game.getWorld().canGo(next.x, next.y)) {
				x = next.x;
				y = next.y;
			} else {
				finished = true;
				game.addLightSource(new Explosion(next.x, next.y));

			}
		} else {
			finished = true;
			game.addLightSource(new Explosion(x, y));
		}

		// Tile tile = game.getWorld().getTile(x, y);
		// if (!tile.isEmpty()) {
		// finished = true;
		// if (tile.getElement() instanceof Living)
		// boom(game, (Living) tile.getElement());
		// return;
		// }
		// for (int i = 0; i < this.speed; i++) {
		// if (this.segment.size() > 0) {
		// Point p = this.segment.remove(0);
		//
		// Tile next = game.getWorld().getTile(p.x, p.y);
		// if (next != null) {
		// if (tile != null) {
		// if (tile.getElement() != null) {
		// this.finished = true;
		//
		// if (!tile.isEmpty()) {
		// finished = true;
		// if (tile.getElement() instanceof Living)
		// boom(game, (Living) tile.getElement());
		// }
		// } else if (!next.canWalkOn()) {
		// this.finished = true;
		// } else {
		// this.x = p.x;
		// this.y = p.y;
		// }
		// }
		// }
		// } else {
		// this.finished = true;
		// game.addLightSource(new Explosion(x, y));
		// }
		// }
	}

	private void boom(Game game, Living living) {
		living.injured(game, this);
		game.addLightSource(new Explosion(x, y));
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

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	/* */

	public static class Factory {

		public static Projectile createFireball(int x, int y, int ciblex, int cibley) {
			Projectile p = new Projectile(x, y, ciblex, cibley);
			p.actionsMax = 2;
			p.depht = 12;
			p.speed = 1;
			p.opaque = false;
			p.tile = Tile.Factory.getFireball();
			p.init();
			return p;
		}
	}

}
