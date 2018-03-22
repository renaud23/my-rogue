package com.renaud.rogue.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.renaud.rogue.element.Element;
import com.renaud.rogue.element.Joueur;
import com.renaud.rogue.element.LightSource;
import com.renaud.rogue.element.Monster;
import com.renaud.rogue.element.light.Torche;
import com.renaud.rogue.element.light.TorcheFixe;
import com.renaud.rogue.element.monster.Wolf;
import com.renaud.rogue.element.projectile.Projectile;
import com.renaud.rogue.event.KeyboardEvent;
import com.renaud.rogue.inventaire.GunAmmo;
import com.renaud.rogue.tools.Point;
import com.renaud.rogue.weapon.Gun;
import com.renaud.rogue.weapon.NoWeapon;
import com.renaud.rogue.weapon.Weapon;
import com.renaud.rogue.world.Light;
import com.renaud.rogue.world.Tile;
import com.renaud.rogue.world.World;

public class Game implements RogueSequence, KeyboardEvent {

    private boolean shoot;
    private boolean activate;
    private boolean weaponAiming;
    private boolean activateAiming;
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
	for (int i = 0; i < 2; i++) {
	    Point start = world.peekEmptyPlace();
	    Monster monster = Monster.Factory.createGhool(start.x, start.y);
	    monsters.add(monster);
	    setElement(monster);
	}
	for (int i = 0; i < 5; i++) {
	    Point start = world.peekEmptyPlace();
	    Monster monster = new Wolf(start.x, start.y);
	    monsters.add(monster);
	    setElement(monster);
	}

	for (int i = 0; i < 5; i++) {
	    Point start = world.peekEmptyPlace();
	    world.getTile(start.x, start.y).addItem(new GunAmmo());
	}

	for (int i = 0; i < 2; i++) {
	    Point start = world.peekEmptyPlace();
	    world.getTile(start.x, start.y).addItem(new Gun());
	}

	for (TorcheFixe torche : world.getTorches()) {
	    this.addLightSource(torche);
	    this.setElement(torche);
	}
	world.print(System.out, true);
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
	lightSources.removeIf(l -> l.isEnd());
	for (LightSource ls : lightSources) {
	    ls.illumine(this);
	}
    }

    @Override
    public void activate() {
	this.illumine();
	if (shoot) {
	    shoot = false;
	    this.joueur.shoot(this);
	} else if (activate) {
	    activate = false;
	    this.joueur.activate(this);
	}
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
    public void activatePressed() {

	if (!activateAiming) {
	    activateAiming = true;
	    this.joueur.resetAimingForActivate();
	    this.currentSequence = aimSequence;
	}
    }

    @Override
    public void weaponPressed() {
	if (joueur.getActiveWeapon() instanceof NoWeapon)
	    return;
	if (activateAiming) {
	    activateAiming = false;
	    this.currentSequence = playSequence;
	    activate = true;
	} else if (!weaponAiming) {
	    weaponAiming = true;
	    this.joueur.resetAimingForShoot();
	    this.currentSequence = aimSequence;
	} else {
	    weaponAiming = false;
	    shoot = true;
	    this.currentSequence = playSequence;
	    playSequence.weaponPressed();
	}
    }

    @Override
    public void switchWeaponPressed() {
	this.currentSequence.switchWeaponPressed();
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
	return weaponAiming || activateAiming;
    }
    /* */

    public void addProjectile(Projectile p) {
	this.projectiles.add(p);
    }

    public void addMonster(Monster monster) {
	Tile tile = this.world.getTile(monster.getX(), monster.getY());
	if (tile.isEmpty()) {
	    this.monsters.add(monster);
	    tile.setOccupant(monster);
	}
    }

    public void removeMonster(Monster monster) {
	this.monsters.remove(monster);
	this.removeElement(monster);
    }

    public List<LightSource> getLightSources() {
	return lightSources;
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
		if (rand.nextInt(10) > 3 || (i == 0 && j == 0)) {
		    Tile tile = this.world.getTile(x + i, y + j);
		    float how = 0.4f + rand.nextInt(2) / 10f;
		    float r = (tile.getColor() >> 16) & 0xFF;
		    r *= how;
		    int ri = (int) (r + 0x8A * (1.0f - how));
		    float g = (tile.getColor() >> 8) & 0xFF;
		    g *= how;
		    int gi = (int) (g + 0x07 * (1.0f - how));
		    float b = (tile.getColor() >> 0) & 0xFF;
		    b *= how;
		    int bi = (int) (b + 0x07 * (1.0f - how));

		    int color = (ri << 16) | (gi << 8) | (bi << 0);

		    tile.setColor(color);
		}
	    }
	}
    }

    public Weapon getActiveWeapon() {
	return joueur.getActiveWeapon();
    }

}
