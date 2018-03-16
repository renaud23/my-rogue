package com.renaud.rogue.game;

import com.renaud.rogue.element.Joueur;
import com.renaud.rogue.event.KeyboardEvent;
import com.renaud.rogue.world.World;

public class Game implements RogueSequence, KeyboardEvent {

	private World world;
	private Joueur joueur;
	private int actions;
	private int actionsMax = 2;
	private int step;

	public Game(World world, Joueur joueur) {
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
		actions--;
	}

	@Override
	public void keyDownPressed() {
		actions--;
	}

	@Override
	public void keyLeftPressed() {
		actions--;
	}

	@Override
	public void keyRightPressed() {
		actions--;
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
