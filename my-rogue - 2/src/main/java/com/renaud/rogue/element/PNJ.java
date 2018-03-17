package com.renaud.rogue.element;

import com.renaud.rogue.game.Game;

public interface PNJ extends Element, TurnPlay {

    void addX(int dx);

    void addY(int dy);

    void activate(Game game);

    boolean isDead();

    int getDepht();

}
