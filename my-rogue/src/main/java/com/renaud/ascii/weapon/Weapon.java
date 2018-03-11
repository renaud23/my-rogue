package com.renaud.ascii.weapon;

import com.renaud.ascii.world.World;

public interface Weapon {
	int getDepht();

	void shoot(World world);
}
