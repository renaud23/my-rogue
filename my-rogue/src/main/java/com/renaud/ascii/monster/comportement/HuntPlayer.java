package com.renaud.ascii.monster.comportement;

import com.renaud.ascii.monster.element.Monster;
import com.renaud.ascii.world.World;

public class HuntPlayer implements Comportement {

	Monster monster;
	GoTo goTo;
	boolean isFinished;

	public HuntPlayer(Monster monster) {
		this.monster = monster;
		// goTo = new GoTo(monster, nx, ny);
	}

	@Override
	public void activate(World world) {
		if (goTo == null) {
			goTo = new GoTo(monster, world.getJoueur().getX(), world.getJoueur().getY());
		} else if (world.canSeePlayer(monster)) {
			goTo.reset(world.getJoueur().getX(), world.getJoueur().getY());
		} else {
			isFinished = true;
		}

		goTo.activate(world);

	}

	@Override
	public void reset() {
		isFinished = false;
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

}
