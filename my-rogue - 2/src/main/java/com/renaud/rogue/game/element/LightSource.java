package com.renaud.rogue.game.element;

import com.renaud.rogue.game.sequence.Game;

public interface LightSource {

	void illumine(Game game);

	default boolean isEnd() {
		return false;
	};
}
