package com.renaud.rogue.element.light;

import com.renaud.rogue.element.Element;
import com.renaud.rogue.element.LightSource;
import com.renaud.rogue.game.Game;
import com.renaud.rogue.tools.Chrono;

public class Explosion implements LightSource, Element {

    private Chrono timeOfLife;
    private Chrono speed;

    private int x;
    private int y;
    int rayon = 5;
    int color = 0xAA5000;
    float alpha = 0.8f;

    public float getAlpha() {
	return alpha;
    }

    boolean start;

    public Explosion(int x, int y) {
	this.x = x;
	this.y = y;

	timeOfLife = new Chrono(450);
	speed = new Chrono(10);
    }

    @Override
    public void illumine(Game game) {
	if (speed.isEllapsed()) {
	    rayon += 2;
	    alpha *= 0.5f;

	    float r = (color >> 16) & 0xFF;
	    float g = (color >> 8) & 0xFF;
	    float b = (color >> 0) & 0xFF;
	    int ri = (int) (r * 1.0f);
	    int gi = (int) Math.min(1.0f, g * 1.5f);
	    int bi = (int) (b * 1.0f);
	    color = (ri << 16) | (gi << 8) | (bi << 0);
	}
    }

    @Override
    public boolean isEnd() {
	return timeOfLife.isEllapsed();
    }

    @Override
    public int getX() {
	return x;
    }

    @Override
    public int getY() {
	return y;
    }

    public int getRayon() {
	return rayon;
    }

    public int getColor() {
	return color;
    }

}
