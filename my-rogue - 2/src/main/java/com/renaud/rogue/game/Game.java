package com.renaud.rogue.game;

import java.util.ArrayList;
import java.util.List;

import com.renaud.rogue.element.Element;
import com.renaud.rogue.element.Joueur;
import com.renaud.rogue.element.Monster;
import com.renaud.rogue.element.projectile.Projectile;
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
    private List<Projectile> projectiles = new ArrayList<>();

    private boolean playChange;

    public Game(World world, Joueur joueur) {
	this.world = world;
	this.joueur = joueur;
	this.actions = this.actionsMax;
	setElement(this.joueur);

	for (int i = 0; i < 10; i++) {
	    Point start = world.peekEmptyPlace();
	    Monster monster = Monster.Factory.createGhool(start.x, start.y);// new Wolf(start.x, start.y);
	    monsters.add(monster);
	    setElement(monster);
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
	if (playChange) {
	    playChange = false;
	    projectiles.removeIf(m -> m.isEnd());
	    projectiles.forEach(m -> {
		m.startTurn();
	    });
	    monsters.removeIf(m -> m.isDead());
	    monsters.forEach(m -> {
		m.startTurn();
	    });
	    boolean turnIsFinished = false;
	    while (!turnIsFinished) {
		turnIsFinished = true;

		for (Projectile proj : projectiles) {
		    if (!proj.turnIsEnd()) {
			turnIsFinished = false;
			proj.activate(this);
		    }
		}

		if (actions <= 0) {
		    // next turn
		    for (Monster monster : monsters) {
			if (!monster.turnIsEnd()) {
			    turnIsFinished = false;
			    monster.activate(this);
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
	if (world.getTile(joueur.getX(), joueur.getY() - 1).canWalkOn()) {
	    moveTo(joueur, joueur.getX(), joueur.getY() - 1);
	    actions--;
	    playChange = true;
	}
    }

    @Override
    public void keyDownPressed() {
	if (world.getTile(joueur.getX(), joueur.getY() + 1).canWalkOn()) {
	    moveTo(joueur, joueur.getX(), joueur.getY() + 1);
	    actions--;
	    playChange = true;
	}
    }

    @Override
    public void keyLeftPressed() {
	if (world.getTile(joueur.getX() - 1, joueur.getY()).canWalkOn()) {
	    moveTo(joueur, joueur.getX() - 1, joueur.getY());
	    actions--;
	    playChange = true;
	}
    }

    @Override
    public void keyRightPressed() {
	if (world.getTile(joueur.getX() + 1, joueur.getY()).canWalkOn()) {
	    moveTo(joueur, joueur.getX() + 1, joueur.getY());
	    actions--;
	    playChange = true;
	}
    }

    @Override
    public void spacePressed() {
	actions--;
    }

    public void moveTo(Element element, int x, int y) {
	this.removeElement(element);
	element.setX(x);
	element.setY(y);
	this.world.setElement(element, x, y);
    }

    public void removeElement(Element element) {
	this.world.removeElement(element.getX(), element.getY());
    }

    public void setElement(Element element) {
	this.world.setElement(element, element.getX(), element.getY());
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

    public void addProjectile(Projectile p) {
	this.projectiles.add(p);
	this.setElement(p);
    }

}
