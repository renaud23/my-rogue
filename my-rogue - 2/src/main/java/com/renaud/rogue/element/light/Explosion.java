package com.renaud.rogue.element.light;

import com.renaud.rogue.drawer.sprite.ExplosionSprite;
import com.renaud.rogue.element.Element;
import com.renaud.rogue.element.LightSource;
import com.renaud.rogue.game.Game;
import com.renaud.rogue.tools.Chrono;

public class Explosion implements LightSource, Element {

    private Chrono timeOfLife;
    private ExplosionSprite sprite = new ExplosionSprite();

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

    public ExplosionSprite getSprite() {
	return sprite;
    }

    public void setSprite(ExplosionSprite sprite) {
	this.sprite = sprite;
    }

    @Override
    public void illumine(Game game) {
    }

}
