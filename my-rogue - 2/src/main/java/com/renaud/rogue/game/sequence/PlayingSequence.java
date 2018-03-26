package com.renaud.rogue.game.sequence;

import com.renaud.rogue.game.element.Monster;
import com.renaud.rogue.game.element.projectile.Projectile;
import com.renaud.rogue.game.event.ActionEvent;

public class PlayingSequence implements RogueSequence, ActionEvent {

    private Game game;

    public PlayingSequence(Game game) {
	this.game = game;
    }

    @Override
    public void activate() {
	game.illumine();
	if (game.isPlayFinished()) {
	    game.getProjectiles().removeIf(m -> m.isEnd());
	    game.getProjectiles().forEach(m -> {
		m.startTurn();
	    });
	    // game.getMonsters().removeIf(m -> m.isDead());
	    game.getMonsters().forEach(m -> {
		m.startTurn();
	    });
	    boolean turnIsFinished = false;
	    while (!turnIsFinished) {
		turnIsFinished = true;

		for (Projectile proj : game.getProjectiles()) {
		    if (!proj.turnIsEnd()) {
			turnIsFinished = false;
			proj.activate(game);
		    }
		}

		if (game.isTurnFinished()) {
		    // next turn
		    for (Monster monster : game.getMonsters()) {
			if (!monster.turnIsEnd()) {
			    turnIsFinished = false;
			    monster.activate(game);
			}
		    }
		}
	    }
	    if (game.isTurnFinished()) {
		game.startNextTurn();
	    }
	    game.startNextPlay();
	}
    }

    @Override
    public void goUpAction() {
	if (game.getWorld().getTile(game.getJoueur().getX(), game.getJoueur().getY() - 1).canWalkOn()) {
	    game.moveTo(game.getJoueur(), game.getJoueur().getX(), game.getJoueur().getY() - 1);
	    game.playFinished();
	}
    }

    @Override
    public void goDownAction() {
	if (game.getWorld().getTile(game.getJoueur().getX(), game.getJoueur().getY() + 1).canWalkOn()) {
	    game.moveTo(game.getJoueur(), game.getJoueur().getX(), game.getJoueur().getY() + 1);
	    game.playFinished();
	}
    }

    @Override
    public void goLeftAction() {
	if (game.getWorld().getTile(game.getJoueur().getX() - 1, game.getJoueur().getY()).canWalkOn()) {
	    game.moveTo(game.getJoueur(), game.getJoueur().getX() - 1, game.getJoueur().getY());
	    game.playFinished();
	}
    }

    @Override
    public void goRightAction() {
	if (game.getWorld().getTile(game.getJoueur().getX() + 1, game.getJoueur().getY()).canWalkOn()) {
	    game.moveTo(game.getJoueur(), game.getJoueur().getX() + 1, game.getJoueur().getY());
	    game.playFinished();
	}
    }

    @Override
    public void weaponAction() {
	game.changeSequence(new AimSequence(game, new ShootingAiming(game.getJoueur())));
	game.playFinished();
    }

    @Override
    public void switchWeaponAction() {
	game.getJoueur().switchWeapon();
	game.playFinished();
    }

    @Override
    public void activateAction() {
	game.changeSequence(new AimSequence(game, new ActivateAiming(game)));
    }

}
