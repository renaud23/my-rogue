package com.renaud.rogue.game;

import java.util.ArrayList;
import java.util.List;

import com.renaud.rogue.element.Joueur;
import com.renaud.rogue.element.monster.Monster;
import com.renaud.rogue.element.monster.Wolf;
import com.renaud.rogue.event.KeyboardEvent;
import com.renaud.rogue.tools.Point;
import com.renaud.rogue.world.World;

public class Game implements RogueSequence, KeyboardEvent {

    private World world;
    private Joueur joueur;
    private int actions;
    private int actionsMax = 2;
    private int step;
    private List<Monster> monsters = new ArrayList<>();

    public Game(World world, Joueur joueur) {
	this.world = world;
	this.joueur = joueur;
	this.actions = this.actionsMax;
	this.world.setElement(joueur.getX(), joueur.getY(), this.joueur);

	for (int i = 0; i < 12; i++) {
	    Point start = world.peekEmptyPlace();
	    Monster monster = new Wolf(start.x, start.y);
	    monsters.add(monster);
	    world.setElement(start.x, start.y, monster);
	}
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

	    monsters.removeIf(m -> m.isDead());
	    monsters.forEach(m -> {
		m.startTurn();
	    });

	    boolean turnIsFinished = false;
	    while (!turnIsFinished) {
		turnIsFinished = true;
		for (Monster monster : monsters) {
		    if (!monster.turnIsEnd()) {
			turnIsFinished = false;
			monster.activate(this);
		    }
		}
	    }
	}
    }

    @Override
    public void keyUpPressed() {
	if (world.getTile(joueur.getX(), joueur.getY() - 1).canWalkOn()) {
	    world.setElement(joueur.getX(), joueur.getY(), null);
	    joueur.goUp();
	    world.setElement(joueur.getX(), joueur.getY(), joueur);
	    actions--;
	}
    }

    @Override
    public void keyDownPressed() {
	if (world.getTile(joueur.getX(), joueur.getY() + 1).canWalkOn()) {
	    world.setElement(joueur.getX(), joueur.getY(), null);
	    joueur.goDown();
	    world.setElement(joueur.getX(), joueur.getY(), joueur);
	    actions--;
	}
    }

    @Override
    public void keyLeftPressed() {
	if (world.getTile(joueur.getX() - 1, joueur.getY()).canWalkOn()) {
	    world.setElement(joueur.getX(), joueur.getY(), null);
	    joueur.goLeft();
	    world.setElement(joueur.getX(), joueur.getY(), joueur);
	    actions--;
	}
    }

    @Override
    public void keyRightPressed() {
	if (world.getTile(joueur.getX() + 1, joueur.getY()).canWalkOn()) {
	    world.setElement(joueur.getX(), joueur.getY(), null);
	    joueur.goRight();
	    world.setElement(joueur.getX(), joueur.getY(), joueur);
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

    public List<Monster> getMonsters() {
	return monsters;
    }

}
