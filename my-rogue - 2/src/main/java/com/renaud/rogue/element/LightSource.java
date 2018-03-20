package com.renaud.rogue.element;

import com.renaud.rogue.game.Game;

public interface LightSource {

	void illumine(Game game);

	default boolean isEnd() {
		return false;
	};
}
