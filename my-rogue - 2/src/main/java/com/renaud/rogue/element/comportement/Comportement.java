package com.renaud.rogue.element.comportement;

import com.renaud.rogue.game.Game;

public interface Comportement {
	void activate(Game game);

	boolean isFinished();

	void reset();
}
