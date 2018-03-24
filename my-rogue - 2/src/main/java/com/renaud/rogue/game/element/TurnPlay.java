package com.renaud.rogue.game.element;

import com.renaud.rogue.game.sequence.Game;

public interface TurnPlay {
    void startTurn();

    boolean turnIsEnd();

    void activate(Game game);

}
