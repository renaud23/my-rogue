package com.renaud.ascii.weapon;

import java.util.ArrayList;
import java.util.List;

import com.renaud.ascii.world.World;

public class Knife implements MeleeWeapon {

	@Override
	public List<Shoot> shoot(World world, int x, int y) {
		List<Shoot> shoots = new ArrayList<>();
		shoots.add(new Impact(this, x, y));
		return shoots;
	}

	@Override
	public int getDepht() {
		return 1;
	}

}
