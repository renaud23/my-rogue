package com.renaud.rogue.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.renaud.rogue.element.Element;
import com.renaud.rogue.element.Joueur;
import com.renaud.rogue.element.LightSource;
import com.renaud.rogue.element.Monster;
import com.renaud.rogue.element.Torche;
import com.renaud.rogue.element.TorcheFixe;
import com.renaud.rogue.element.monster.Wolf;
import com.renaud.rogue.element.projectile.Projectile;
import com.renaud.rogue.event.KeyboardEvent;
import com.renaud.rogue.tools.Point;
import com.renaud.rogue.world.Light;
import com.renaud.rogue.world.Tile;
import com.renaud.rogue.world.World;

public class Game implements RogueSequence, KeyboardEvent {

    private boolean aimToShoot;
    private World world;
    private Joueur joueur;

    private List<Monster> monsters = new ArrayList<>();
    private List<Projectile> projectiles = new ArrayList<>();
    private List<LightSource> lightSources = new ArrayList<>();

    private PlaySequence playSequence;
    private AimSequence aimSequence;
    private RogueSequence currentSequence;

    public Game(World world, Joueur joueur) {
	this.world = world;
	this.joueur = joueur;
	setElement(this.joueur);

	this.playSequence = new PlaySequence(this);
	this.aimSequence = new AimSequence(this);
	this.currentSequence = this.playSequence;

	// for dev
	for (int i = 0; i < 10; i++) {
	    Point start = world.peekEmptyPlace();
	    Monster monster = Monster.Factory.createGhool(start.x, start.y);
	    monsters.add(monster);
	    setElement(monster);
	}
	for (int i = 0; i < 15; i++) {
	    Point start = world.peekEmptyPlace();
	    Monster monster = new Wolf(start.x, start.y);
	    monsters.add(monster);
	    setElement(monster);
	}
	for (int i = 0; i < 30; i++) {
	    Point start = world.peekEmptyPlace();
	    TorcheFixe torche = new TorcheFixe(start.x, start.y);
	    this.addLightSource(torche);
	    this.setElement(torche);
	}
	this.addLightSource(new Torche(joueur));
    }

    public World getWorld() {
	return world;
    }

    public Joueur getJoueur() {
	return joueur;
    }

    private void illumine() {
	for (int i = 0; i < this.world.getSize(); i++) {
	    this.world.getTile(i).setLight(Light.DARK);
	}
	for (LightSource ls : lightSources) {
	    ls.illumine(this);
	}
    }

    @Override
    public void activate() {
	this.illumine();
	this.playSequence.activate();
    }

    @Override
    public void keyUpPressed() {
	this.currentSequence.keyUpPressed();
    }

    @Override
    public void keyDownPressed() {
	this.currentSequence.keyDownPressed();
    }

    @Override
    public void keyLeftPressed() {
	this.currentSequence.keyLeftPressed();
    }

    @Override
    public void keyRightPressed() {
	this.currentSequence.keyRightPressed();
    }

    @Override
    public void spacePressed() {
	if (!aimToShoot) {
	    aimToShoot = true;
	    this.joueur.resetAim();
	    this.currentSequence = aimSequence;
	} else {
	    aimToShoot = false;
	    this.currentSequence = playSequence;
	    this.joueur.getWeapon().shoot(this, this.joueur.getAimx(), joueur.getAimy());
	    playSequence.spacePressed();
	}
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
	return this.playSequence.getActions();
    }

    public int getActionsMax() {
	return this.playSequence.getActionsMax();
    }

    public int getStep() {
	return this.playSequence.getStep();
    }

    public List<Monster> getMonsters() {
	return monsters;
    }

    public List<Projectile> getProjectiles() {
	return projectiles;
    }

    public boolean isAiming() {
	return aimToShoot;
    }
    /* */

    public void addProjectile(Projectile p) {
	this.projectiles.add(p);
    }

    public void addMonster(Monster monster) {
	Tile tile = this.world.getTile(monster.getX(), monster.getY());
	if (tile.isEmpty()) {
	    this.monsters.add(monster);
	    tile.setElement(monster);
	}
    }

    public void removeMonster(Monster monster) {
	this.monsters.remove(monster);
	this.removeElement(monster);
    }

    public void addLightSource(LightSource ls) {
	this.lightSources.add(ls);
    }

    public void removeLightSource(LightSource ls) {
	this.lightSources.remove(ls);
    }

    public void bloodify(int x, int y) {
	Random rand = new Random();
	for (int i = -1; i <= 1; i++) {
	    for (int j = -1; j <= 1; j++) {
		if (rand.nextInt(10) > 7 || (i == 0 && j == 0)) {
		    Tile tile = this.world.getTile(x + i, y + j);
		    tile.setColor(0x8A0707);
		}
	    }
	}

	// this.world.setTile(x, y, Tile.Factory.getBlood(tile));
    }

}
