package com.renaud.rogue.game.element.comportement;

import java.util.List;

import com.renaud.rogue.game.element.Element;
import com.renaud.rogue.game.element.comportement.pathfinding.AStar;
import com.renaud.rogue.game.element.comportement.pathfinding.PathFinding;
import com.renaud.rogue.game.sequence.Game;
import com.renaud.rogue.game.tools.Point;

public class TrackPlayer implements Comportement {

	private Element l;
	private int lastx = Integer.MAX_VALUE;
	private int lasty = Integer.MAX_VALUE;
	private List<Point> path;
	private boolean finished;

	public TrackPlayer(Element l) {
		this.l = l;
	}

	@Override
	public void activate(Game game) {
		if (lastx != game.getJoueur().getX() || lasty != game.getJoueur().getY()) {
			PathFinding theWay = new AStar(game, new Point(l.getX(), l.getY()), new Point(game.getJoueur().getX(), game.getJoueur().getY()));
			path = theWay.getPath();
			if (!path.isEmpty()) {
				path.remove(0);// l'élèment est sur celle-ci
			}
			lastx = game.getJoueur().getX();
			lasty = game.getJoueur().getY();
		}
		if (!path.isEmpty()) {
			Point next = path.remove(0);
			if (game.getWorld().getTile(next.x, next.y).canWalkOn()) {
				game.moveTo(l, next.x, next.y);
			} else {
				finished = true;
			}
		} else {
			finished = true;
		}
	}

	@Override
	public void reset() {
		finished = false;
		path.clear();
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

}