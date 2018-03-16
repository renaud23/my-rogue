package com.renaud.rogue.comportement;

import com.renaud.rogue.game.Game;
import com.renaud.rogue.monster.Monster;

public class HuntPlayer implements Comportement {

    Monster monster;
    GoTo goTo;
    boolean isFinished;

    public HuntPlayer(Monster monster) {
	this.monster = monster;
    }

    @Override
    public void activate(Game game) {
	if (goTo == null) {
	    goTo = new GoTo(monster, game.getJoueur().getX(), game.getJoueur().getY());
	} else if (game.getWorld().canSee(monster, game.getJoueur())) {
	    goTo.reset(game.getJoueur().getX(), game.getJoueur().getY());
	} else {
	    isFinished = true;
	}

	goTo.activate(game);

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
