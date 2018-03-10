package com.renaud.ascii.monster.comportement;

import com.renaud.ascii.world.World;

public interface Comportement {
	void activate(World world);

	boolean isFinished();

	void reset();
}
