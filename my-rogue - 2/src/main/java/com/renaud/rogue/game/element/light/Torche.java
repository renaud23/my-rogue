package com.renaud.rogue.game.element.light;

import java.util.Random;

import com.renaud.rogue.game.element.LightSource;
import com.renaud.rogue.game.element.Living;
import com.renaud.rogue.game.tools.Chrono;
import com.renaud.rogue.game.tools.MathTools;
import com.renaud.rogue.game.tools.Point;
import com.renaud.rogue.game.world.Game;
import com.renaud.rogue.game.world.Light;

public class Torche implements LightSource {

    private Living element;
    private Random rand = new Random();
    private int dephtMax = 10;
    private int dephtMin = 8;
    private int dephtVar = 1;
    private int depht = 6;
    private Chrono chrono;

    public Torche(Living element) {
	this.element = element;
	this.chrono = new Chrono(350l);
	depht = dephtMin;
    }

    private void change() {
	if (chrono.isEllapsed()) {
	    depht += dephtVar;
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
	change();
	for (Point p : element.getVisibilityPoints(game)) {
	    int dist = MathTools.distance(p.x, p.y, element.getX(), element.getY());
	    float cube = depht * depht;
	    if (dist < cube) {
		float how = cube / (cube + dist);
		Light li = game.getWorld().getTile(p.x, p.y).getLight();

		float r = Math.min(1.0f, Math.min(1.0f, li.pr + how * 0.5f));
		float g = Math.min(1.0f, Math.min(1.0f, li.pg + how * 0.5f));
		float b = Math.min(1.0f, Math.min(1.0f, li.pb + how * 0.5f));
		game.getWorld().getTile(p.x, p.y).setLight(new Light(r, g, b));
	    }
	}
    }

}
