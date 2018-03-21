package com.renaud.rogue.world;

public class Light {
    public float pr;
    public float pg;
    public float pb;

    public Light(float pr, float pg, float pb) {
	this.pr = pr;
	this.pg = pg;
	this.pb = pb;
    }

    public float getAlpha() {
	return (pr + pg + pb) / 3;
    }

    public static final Light DARK = new Light(0, 0, 0);

}
