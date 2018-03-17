package com.renaud.rogue.element;

import com.renaud.rogue.game.Game;

public interface TurnPlay {
    void startTurn();

    boolean turnIsEnd();

    void activate(Game game);

}
