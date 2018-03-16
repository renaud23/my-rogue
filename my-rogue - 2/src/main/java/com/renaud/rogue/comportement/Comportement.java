package com.renaud.rogue.comportement;

import com.renaud.rogue.game.Game;

public interface Comportement {
	void activate(Game game);

	boolean isFinished();

	void reset();
}
