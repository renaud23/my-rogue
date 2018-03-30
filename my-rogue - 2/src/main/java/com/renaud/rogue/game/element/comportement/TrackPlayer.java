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

	public TrackPlayer(Element l) {
		this.l = l;
	}

	@Override
	public void activate(Game game) {
		if (lastx != game.getJoueur().getX() || lasty != game.getJoueur().getY()) {
			PathFinding theWay = new AStar(game, new Point(l.getX(), l.getY()), new Point(game.getJoueur().getX(), game.getJoueur().getY()));
			path = theWay.getPath();
			lastx = game.getJoueur().getX();
			lasty = game.getJoueur().getY();
		}
		if (!path.isEmpty()) {
			Point next = path.remove(0);
			if (game.getWorld().getTile(next.x, next.y).canWalkOn()) {
				game.moveTo(l, next.x, next.y);
			}
		}

		// Point best = new Point(l.getX(), l.getY());
		// int bestDir = Integer.MAX_VALUE;
		// for (int i = -1; i <= 1; i++) {
		// for (int j = -1; j <= 1; j++) {
		// if ((i == 0 && j == 0) || (i != 0 && j != 0))
		// continue;
		// if (game.getWorld().canGo(l.getX() + i, l.getY() + j)) {
		// int dir = MathTools.distance(l.getX() + i, l.getY() + j, game.getJoueur().getX(), game.getJoueur().getY());
		// if (dir < bestDir) {
		// bestDir = dir;
		// best = new Point(l.getX() + i, l.getY() + j);
		// }
		// }
		// }
		// }
		// game.moveTo(l, best.x, best.y);
	}

}