package com.renaud.rogue.game.element.comportement;

import com.renaud.rogue.game.sequence.Game;

public interface Comportement {
    void activate(Game game);

    default boolean isFinished() {
	return false;
    }

    default void reset() {
    };
}
