package com.renaud.rogue.game.tools;

public class Chrono {
    public long ref;
    public long time;

    public Chrono(long ref) {
	this.ref = ref;
	time = System.currentTimeMillis();
    }

    public boolean isEllapsed() {
	long cur = System.currentTimeMillis();
	if ((cur - time) > ref) {
	    time = cur;
	    return true;
	}
	return false;
    }

}
