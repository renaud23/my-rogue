package com.renaud.rogue.game;

import com.renaud.rogue.element.Monster;
import com.renaud.rogue.element.projectile.Projectile;
import com.renaud.rogue.event.KeyboardEvent;

public class PlaySequence implements RogueSequence, KeyboardEvent {

	private int actionsMax = 2;
	private int step;
	private boolean playChange;
	private int actions;

	private Game game;

	public PlaySequence(Game game) {
		this.game = game;
	}

	@Override
	public void activate() {
		if (playChange) {
			playChange = false;
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

				if (actions <= 0) {
					// next turn
					for (Monster monster : game.getMonsters()) {
						if (!monster.turnIsEnd()) {
							turnIsFinished = false;
							monster.activate(game);
						}
					}
				}
			}

			if (actions <= 0) {
				// next turn
				this.actions = this.actionsMax;
				this.step++;
			}
		}
	}

	@Override
	public void keyUpPressed() {
		if (game.getWorld().getTile(game.getJoueur().getX(), game.getJoueur().getY() - 1).canWalkOn()) {
			game.moveTo(game.getJoueur(), game.getJoueur().getX(), game.getJoueur().getY() - 1);
			actions--;
			playChange = true;
		}
	}

	@Override
	public void keyDownPressed() {
		if (game.getWorld().getTile(game.getJoueur().getX(), game.getJoueur().getY() + 1).canWalkOn()) {
			game.moveTo(game.getJoueur(), game.getJoueur().getX(), game.getJoueur().getY() + 1);
			actions--;
			playChange = true;
		}
	}

	@Override
	public void keyLeftPressed() {
		if (game.getWorld().getTile(game.getJoueur().getX() - 1, game.getJoueur().getY()).canWalkOn()) {
			game.moveTo(game.getJoueur(), game.getJoueur().getX() - 1, game.getJoueur().getY());
			actions--;
			playChange = true;
		}
	}

	@Override
	public void keyRightPressed() {
		if (game.getWorld().getTile(game.getJoueur().getX() + 1, game.getJoueur().getY()).canWalkOn()) {
			game.moveTo(game.getJoueur(), game.getJoueur().getX() + 1, game.getJoueur().getY());
			actions--;
			playChange = true;
		}
	}

	@Override
	public void rankedWeaponPressed() {
		actions--;
		playChange = true;
	}

	public int getActions() {
		return actions;
	}

	public int getActionsMax() {
		return actionsMax;
	}

	public int getStep() {
		return step;
	}

	@Override
	public void switchWeaponPressed() {
		actions--;
		playChange = true;
		game.getJoueur().switchWeapon();
	}

}
