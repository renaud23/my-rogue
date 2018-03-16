package com.renaud.rogue.element;

import com.renaud.rogue.game.Game;

public interface PNJ extends Element {
    int getStep();

    void addX(int dx);

    void addY(int dy);

    void activate(Game game);

    boolean isDead();

    void startTurn();

    boolean turnIsEnd();
}
