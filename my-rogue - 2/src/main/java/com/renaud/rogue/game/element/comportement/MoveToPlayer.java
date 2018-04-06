package com.renaud.rogue.game.element.comportement;

import com.renaud.rogue.game.element.Element;
import com.renaud.rogue.game.tools.MathTools;
import com.renaud.rogue.game.tools.Point;
import com.renaud.rogue.game.world.Game;

public class MoveToPlayer implements Comportement {

	private Element l;

	public MoveToPlayer(Element l) {
		this.l = l;
	}

	@Override
	public void activate(Game game) {
		Point best = new Point(l.getX(), l.getY());
		int bestDir = Integer.MAX_VALUE;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if ((i == 0 && j == 0) || (i != 0 && j != 0))
					continue;
				if (game.getWorld().canGo(l.getX() + i, l.getY() + j)) {
					int dir = MathTools.distance(l.getX() + i, l.getY() + j, game.getJoueur().getX(), game.getJoueur().getY());
					if (dir < bestDir) {
						bestDir = dir;
						best = new Point(l.getX() + i, l.getY() + j);
					}
				}
			}
		}
		game.moveTo(l, best.x, best.y);
	}

}