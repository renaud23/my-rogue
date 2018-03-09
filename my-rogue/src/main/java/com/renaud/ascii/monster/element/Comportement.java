package com.renaud.ascii.monster.element;

import com.renaud.ascii.world.World;

public interface Comportement {
	void activate(World world);

	void reset();
}
