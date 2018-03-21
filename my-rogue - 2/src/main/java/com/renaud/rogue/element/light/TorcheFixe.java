package com.renaud.rogue.element.light;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.renaud.rogue.element.Element;
import com.renaud.rogue.element.LightSource;
import com.renaud.rogue.game.Game;
import com.renaud.rogue.tools.Chrono;
import com.renaud.rogue.tools.MathTools;
import com.renaud.rogue.tools.Point;
import com.renaud.rogue.world.Light;
import com.renaud.rogue.world.Tile;

public class TorcheFixe implements LightSource, Element {

    private int x;
    private int y;

    private Chrono chrono;
    private Random rand = new Random();
    private int dephtMax = 10;
    private int dephtMin = 8;
    private int dephtVar = 1;
    private int depht = 8;
    private Tile tile = Tile.Factory.getTorche();

    private Set<Point> visibility;

    public TorcheFixe(int x, int y) {
	this.x = x;
	this.y = y;
	this.chrono = new Chrono(350l);
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

    private void change() {
	if (chrono.isEllapsed()) {
	    depht += dephtVar;
	    visibility = null;
	    if (rand.nextBoolean()) {
		dephtVar *= -1;
	    }
	    if (depht == dephtMax) {
		dephtVar = -1;
	    } else if (depht == dephtMin) {
		dephtVar = 1;
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

		float r = Math.min(1.0f, Math.min(1.0f, li.pr + how * 0.4f));
		float g = Math.min(1.0f, Math.min(1.0f, li.pg + how * 0.4f));
		float b = Math.min(1.0f, Math.min(1.0f, li.pb + how * 0.1f));
		game.getWorld().getTile(p.x, p.y).setLight(new Light(r, g, b));

	    }
	}
	change();
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
    public Tile getTile() {
	return tile;
    }

    @Override
    public boolean isOpaque() {
	return false;
    }

}
