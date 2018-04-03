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
		if (SequenceAutomate.getInstance().getPlayingContext().isPlayFinished()) {
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

				if (SequenceAutomate.getInstance().getPlayingContext().isTurnFinished()) {
					// next turn
					for (Monster monster : game.getMonsters()) {
						if (!monster.turnIsEnd()) {
							turnIsFinished = false;
							monster.activate(game);
						}
					}
				}
			}
			if (SequenceAutomate.getInstance().getPlayingContext().isTurnFinished()) {
				SequenceAutomate.getInstance().getPlayingContext().startNextTurn();
			}
			SequenceAutomate.getInstance().getPlayingContext().startNextPlay();
		}
	}

	@Override
	public void goUpAction() {
		if (game.getWorld().getTile(game.getJoueur().getX(), game.getJoueur().getY() - 1).canWalkOn()) {
			game.moveTo(game.getJoueur(), game.getJoueur().getX(), game.getJoueur().getY() - 1);
			SequenceAutomate.getInstance().getPlayingContext().playFinished();
		}
	}

	@Override
	public void goDownAction() {
		if (game.getWorld().getTile(game.getJoueur().getX(), game.getJoueur().getY() + 1).canWalkOn()) {
			game.moveTo(game.getJoueur(), game.getJoueur().getX(), game.getJoueur().getY() + 1);
			SequenceAutomate.getInstance().getPlayingContext().playFinished();
		}
	}

	@Override
	public void goLeftAction() {
		if (game.getWorld().getTile(game.getJoueur().getX() - 1, game.getJoueur().getY()).canWalkOn()) {
			game.moveTo(game.getJoueur(), game.getJoueur().getX() - 1, game.getJoueur().getY());
			SequenceAutomate.getInstance().getPlayingContext().playFinished();
		}
	}

	@Override
	public void goRightAction() {
		if (game.getWorld().getTile(game.getJoueur().getX() + 1, game.getJoueur().getY()).canWalkOn()) {
			game.moveTo(game.getJoueur(), game.getJoueur().getX() + 1, game.getJoueur().getY());
			SequenceAutomate.getInstance().getPlayingContext().playFinished();
		}
	}

	@Override
	public void weaponAction() {
		SequenceAutomate.getInstance().setNextSequence(new AimSequence(game, new ShootingAiming(game.getJoueur())));
		SequenceAutomate.getInstance().getPlayingContext().playFinished();
	}

	@Override
	public void switchWeaponAction() {
		game.getJoueur().switchWeapon();
		SequenceAutomate.getInstance().getPlayingContext().playFinished();
	}

	@Override
	public void useAction() {
		SequenceAutomate.getInstance().setNextSequence(new AimSequence(game, new UseAiming(game)));
	}

	@Override
	public void inventaireAction() {
		SequenceAutomate.getInstance().setNextSequence(new InventorySequence(game));
	}

	@Override
	public void activateAction() {
		SequenceAutomate.getInstance().setNextSequence(new AimSequence(game, new ActivateAiming(game)));
	}

}
