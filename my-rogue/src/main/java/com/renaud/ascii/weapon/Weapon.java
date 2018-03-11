package com.renaud.ascii.weapon;

import java.util.List;

import com.renaud.ascii.world.World;

public interface Weapon {
	int getDepht();

	List<Shoot> shoot(World world, int x, int y);
}
