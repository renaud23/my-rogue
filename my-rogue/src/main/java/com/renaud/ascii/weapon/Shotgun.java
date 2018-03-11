package com.renaud.ascii.weapon;

import java.util.ArrayList;
import java.util.List;

import com.renaud.ascii.world.World;

public class Shotgun implements ThrowingWeapon {

	@Override
	public List<Shoot> shoot(World world, int x, int y) {
		return new ArrayList<>();

	}

	@Override
	public int getDepht() {
		return 20;
	}

}
