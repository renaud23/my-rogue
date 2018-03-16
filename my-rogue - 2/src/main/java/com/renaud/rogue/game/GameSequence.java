package com.renaud.rogue.game;

import com.renaud.rogue.element.Joueur;
import com.renaud.rogue.event.KeyboardEvent;
import com.renaud.rogue.world.World;

public class GameSequence implements RogueSequence, KeyboardEvent {

	private World world;
	private Joueur joueur;
	private int actions;
	private int actionsMax = 2;
	private int step;

	public GameSequence(World world, Joueur joueur) {
		this.world = world;
		this.joueur = joueur;
		this.actions = this.actionsMax;
		this.world.setElement(joueur.getX(), joueur.getY(), this.joueur);
	}

	public World getWorld() {
		return world;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	@Override
	public void activate() {
		if (actions == 0) {
			// next turn
			this.actions = this.actionsMax;
			this.step++;

			System.out.println("next turn " + this.step);
		}

	}

	@Override
	public void keyUpPressed() {
		if (world.getTile(joueur.getX(), joueur.getY() - 1).canWalkOn()) {
			world.setElement(joueur.getX(), joueur.getY(), null);
			joueur.goUp();
			actions--;
		}
	}

	@Override
	public void keyDownPressed() {
		if (world.getTile(joueur.getX(), joueur.getY() + 1).canWalkOn()) {
			world.setElement(joueur.getX(), joueur.getY(), null);
			joueur.goDown();
			actions--;
		}
	}

	@Override
	public void keyLeftPressed() {
		if (world.getTile(joueur.getX() - 1, joueur.getY()).canWalkOn()) {
			world.setElement(joueur.getX(), joueur.getY(), null);
			joueur.goLeft();
			actions--;
		}
	}

	@Override
	public void keyRightPressed() {
		if (world.getTile(joueur.getX() + 1, joueur.getY()).canWalkOn()) {
			world.setElement(joueur.getX(), joueur.getY(), null);
			joueur.goRight();
			actions--;
		}
	}

	@Override
	public void spacePressed() {
		actions--;
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

}
