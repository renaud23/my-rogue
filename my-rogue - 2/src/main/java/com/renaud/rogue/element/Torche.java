package com.renaud.rogue.element;

import com.renaud.rogue.game.Game;
import com.renaud.rogue.tools.MathTools;
import com.renaud.rogue.tools.Point;
import com.renaud.rogue.world.Light;

public class Torche implements LightSource {

	private Living element;

	private int depht = 8;

	public Torche(Living element) {
		this.element = element;
	}

	@Override
	public void illumine(Game game) {
		for (Point p : element.getVisibilityPoints(game)) {
			int dist = MathTools.distance(p.x, p.y, element.getX(), element.getY());
			float cube = depht * depht;
			if (dist < cube) {
				float how = cube / (cube + dist);
				Light li = game.getWorld().getTile(p.x, p.y).getLight();

				float r = Math.min(1.0f, Math.min(1.0f, li.pr + how * 0.8f));
				float g = Math.min(1.0f, Math.min(1.0f, li.pg + how * 0.8f));
				float b = Math.min(1.0f, Math.min(1.0f, li.pb + how * 0.8f));
				game
					.getWorld()
					.getTile(p.x, p.y)
					.setLight(new Light(r, g, b));
			}
		}
	}

}
