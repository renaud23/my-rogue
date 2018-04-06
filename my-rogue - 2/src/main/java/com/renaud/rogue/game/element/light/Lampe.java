package com.renaud.rogue.game.element.light;

import java.util.HashSet;
import java.util.Set;

import com.renaud.rogue.game.element.PhysicalLightSource;
import com.renaud.rogue.game.element.TileElement;
import com.renaud.rogue.game.tools.MathTools;
import com.renaud.rogue.game.tools.Point;
import com.renaud.rogue.game.world.Game;
import com.renaud.rogue.game.world.Light;

public class Lampe implements PhysicalLightSource {

	private int x;
	private int y;

	private int depht = 4;
	private TileElement tile = TileElement.Factory.getTorche();

	private Set<Point> visibility;

	public Lampe(int x, int y) {
		this.x = x;
		this.y = y;

	}

	private void init(Game game) {
		visibility = new HashSet<>();
		for (int i = -depht; i <= depht; i++) {
			for (int j = -depht; j <= depht; j++) {
				int xi = this.x - i;
				int yi = this.y - j;
				if (xi < 0 || yi < 0 || xi >= game.getWorld().getWidth() || yi >= game.getWorld().getHeight()) {
					continue;
				}
				if (MathTools.distance(xi, yi, this.x, this.y) < this.depht * this.depht) {
					boolean visible = true;
					for (Point p : MathTools.getSegment(x, y, xi, yi)) {
						if (p.x == xi && p.y == yi) {
							continue;
						}

						if (!game.getWorld().getTile(p.x, p.y).canSeeThrought()) {
							visible = false;
						}

					}
					if (visible) {
						visibility.add(new Point(xi, yi));
					}
				}
			}
		}
	}

	@Override
	public void illumine(Game game) {
		if (visibility == null) {
			init(game);
		}

		for (Point p : visibility) {
			int dist = MathTools.distance(p.x, p.y, x, y);
			float cube = depht * depht;
			if (dist < cube) {
				float how = cube / (cube + dist);
				Light li = game.getWorld().getTile(p.x, p.y).getLight();

				float r = Math.min(1.0f, Math.min(1.0f, li.pr + how * 0.5f));
				float g = Math.min(1.0f, Math.min(1.0f, li.pg + how * 0.2f));
				float b = Math.min(1.0f, Math.min(1.0f, li.pb + how * 0.2f));
				game.getWorld().getTile(p.x, p.y).setLight(new Light(r, g, b));

			}
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public TileElement getTile() {
		return tile;
	}

	@Override
	public boolean isOpaque() {
		return false;
	}

}
