package com.renaud.rogue.element;

import com.renaud.rogue.sequence.Game;

public interface LightSource {

	void illumine(Game game);

	default boolean isEnd() {
		return false;
	};
}
