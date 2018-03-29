package com.renaud.rogue.game.sequence;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.renaud.rogue.game.element.Element;
import com.renaud.rogue.game.element.Joueur;
import com.renaud.rogue.game.element.LightSource;
import com.renaud.rogue.game.element.Monster;
import com.renaud.rogue.game.element.light.Torche;
import com.renaud.rogue.game.element.light.TorcheFixe;
import com.renaud.rogue.game.element.monster.Wolf;
import com.renaud.rogue.game.element.projectile.Projectile;
import com.renaud.rogue.game.event.ActionEvent;
import com.renaud.rogue.game.inventaire.Chargeur9mm;
import com.renaud.rogue.game.tools.Point;
import com.renaud.rogue.game.weapon.BerettaM9;
import com.renaud.rogue.game.world.Light;
import com.renaud.rogue.game.world.TileDungeon;
import com.renaud.rogue.game.world.World;
import com.renaud.rogue.layout.loot.InventoryLayout;
import com.renaud.rogue.layout.loot.LootLayout;

public class Game implements RogueSequence, ActionEvent {

	public final static int ACTION_MAX = 2;
	private int step;
	private int actions = ACTION_MAX;
	private boolean playFinished;

	private World world;
	private Joueur joueur;

	private List<Monster> monsters = new ArrayList<>();
	private List<Projectile> projectiles = new ArrayList<>();
	private List<LightSource> lightSources = new ArrayList<>();

	private RogueSequence currentSequence;

	private LootLayout lootLayout;
	private InventoryLayout inventoryLayout;
	private boolean onLoot;

	public Game(World world, Joueur joueur, LootLayout lootLayout, InventoryLayout inventoryLayout) {
		this.lootLayout = lootLayout;
		this.inventoryLayout = inventoryLayout;
		this.world = world;
		this.joueur = joueur;
		setElement(this.joueur);
		this.currentSequence = new PlayingSequence(this);

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

		for (int i = 0; i < 20; i++) {
			Point start = world.peekEmptyPlace();
			world.getTile(start.x, start.y).addItem(new Chargeur9mm());
		}

		for (int i = 0; i < 20; i++) {
			Point start = world.peekEmptyPlace();
			world.getTile(start.x, start.y).addItem(new BerettaM9());
		}

		for (TorcheFixe torche : world.getTorches()) {
			this.addLightSource(torche);
			this.setElement(torche);
		}
		world.print(System.out, true);
		this.addLightSource(new Torche(joueur));
	}

	public LootLayout getLootLayout() {
		return lootLayout;
	}

	public World getWorld() {
		return world;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	public void illumine() {
		for (int i = 0; i < this.world.getSize(); i++) {
			this.world.getTile(i).setLight(Light.DARK);
		}
		lightSources.removeIf(l -> l.isEnd());
		for (LightSource ls : lightSources) {
			ls.illumine(this);
		}
	}

	/* activate sequence */

	@Override
	public void activate() {
		this.currentSequence.activate();
	}

	/* Action Event */

	@Override
	public void goUpAction() {
		this.currentSequence.goUpAction();
	}

	@Override
	public void goDownAction() {
		this.currentSequence.goDownAction();
	}

	@Override
	public void goLeftAction() {
		this.currentSequence.goLeftAction();
	}

	@Override
	public void goRightAction() {
		this.currentSequence.goRightAction();
	}

	@Override
	public void activateAction() {
		this.currentSequence.activateAction();
	}

	@Override
	public void weaponAction() {
		this.currentSequence.weaponAction();
	}

	@Override
	public void switchWeaponAction() {
		this.currentSequence.switchWeaponAction();
	}

	public Game(World world) {
		this.world = world;
	}

	@Override
	public void annulerAction() {
		this.currentSequence.annulerAction();
	}

	public void changeSequence(RogueSequence sequence) {
		this.currentSequence = sequence;
	}

	/* turn play */

	public void playFinished() {
		actions--;
		playFinished = true;
	}

	public boolean isPlayFinished() {
		return playFinished;
	}

	public boolean isTurnFinished() {
		return actions <= 0;
	}

	public void startNextTurn() {
		step++;
		actions = ACTION_MAX;
	}

	public void startNextPlay() {
		playFinished = false;
	}

	/* */

	public void moveTo(Element element, int x, int y) {
		this.removeElement(element);
		element.setX(x);
		element.setY(y);
		this.world.setElement(element, x, y);
	}

	public boolean isAiming() {
		return this.currentSequence instanceof AimSequence;
	}

	public void removeElement(Element element) {
		this.world.removeElement(element.getX(), element.getY());
	}

	public void setElement(Element element) {
		this.world.setElement(element, element.getX(), element.getY());
	}

	public List<Monster> getMonsters() {
		return monsters;
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public void addProjectile(Projectile p) {
		this.projectiles.add(p);
	}

	public void addMonster(Monster monster) {
		TileDungeon tile = this.world.getTile(monster.getX(), monster.getY());
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
					TileDungeon tile = this.world.getTile(x + i, y + j);
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

	public boolean isOnLoot() {
		return this.currentSequence instanceof LootSequence;
	}

	public int getAimingDepht() {
		if (currentSequence instanceof AimSequence) {
			return ((AimSequence) currentSequence).getDepht();
		}
		return -1;
	}

}
