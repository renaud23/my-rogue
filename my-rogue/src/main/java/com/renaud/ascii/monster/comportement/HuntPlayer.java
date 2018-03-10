package com.renaud.ascii.monster.comportement;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.renaud.ascii.figure.Point;
import com.renaud.ascii.monster.element.Monster;
import com.renaud.ascii.world.World;

public class HuntPlayer implements Comportement {

	Monster monster;

	Random r = new Random();
	int nx;
	int ny;
	Set<Point> already = new HashSet<>();

	@Override
	public void activate(World world) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
