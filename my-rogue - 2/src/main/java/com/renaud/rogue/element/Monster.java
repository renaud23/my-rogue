package com.renaud.rogue.element;

import com.renaud.rogue.element.monster.Shooter;

public interface Monster extends Living, TurnPlay {

    void addX(int dx);

    void addY(int dy);

    int getDepht();

    public static class Factory {
	public static Monster createGhool(int x, int y) {
	    Shooter m = new Shooter(x, y);
	    m.depht = 12;
	    m.dephtOfFire = 6;
	    m.life = 10;
	    m.actionsMax = 2;

	    return m;
	}
    }
}
