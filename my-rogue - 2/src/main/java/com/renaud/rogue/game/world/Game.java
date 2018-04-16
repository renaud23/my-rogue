package com.renaud.rogue.game.world;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.renaud.rogue.game.element.Element;
import com.renaud.rogue.game.element.Joueur;
import com.renaud.rogue.game.element.LightSource;
import com.renaud.rogue.game.element.Monster;
import com.renaud.rogue.game.element.light.Torche;
import com.renaud.rogue.game.element.projectile.Projectile;
import com.renaud.rogue.game.event.ActionEvent;
import com.renaud.rogue.game.inventaire.Chargeur9mm;
import com.renaud.rogue.game.sequence.RogueSequence;
import com.renaud.rogue.game.tools.Point;
import com.renaud.rogue.game.weapon.BerettaM9;
import com.renaud.rogue.view.console.ConsoleStream;

public class Game implements RogueSequence, ActionEvent {

	private World world;
	private Joueur joueur;

	private List<Monster> monsters = new ArrayList<>();
	private List<Projectile> projectiles = new ArrayList<>();
	private List<LightSource> lightSources = new ArrayList<>();

	public Game(World world, Joueur joueur) {
		this.world = world;
		this.joueur = joueur;
		setElement(this.joueur);
		this.refreshDungeon();

		world.print(System.out, true);
		System.setOut(new PrintStream(new ConsoleStream()));
		this.addLightSource(new Torche(joueur));
	}

	public void refreshDungeon() {
		monsters.clear();
		for (Monster monster : this.world.getMonsters()) {
			monsters.add(monster);
		}

		for (int i = 0; i < 2; i++) {
			Point start = world.peekEmptyPlace();
			world.getTile(start.x, start.y).addItem(new Chargeur9mm());
		}

		for (int i = 0; i < 1; i++) {
			Point start = world.peekEmptyPlace();
			world.getTile(start.x, start.y).addItem(new BerettaM9());
		}

		for (LightSource torche : world.getTorches()) {
			this.addLightSource(torche);
			this.setElement((Element) torche);
		}
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

	/* */

	public void moveTo(Element element, int x, int y) {
		this.removeElement(element);
		element.setX(x);
		element.setY(y);
		this.world.setElement(element, x, y);
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

	/* */

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

	public void addLightSource(LightSource ls) {
		this.lightSources.add(ls);
	}

	public void removeMonster(Monster monster) {
		this.monsters.remove(monster);
		this.removeElement(monster);
	}

	public void removeLightSource(LightSource ls) {
		this.lightSources.remove(ls);
	}

	public void removeElement(Element element) {
		this.world.removeElement(element.getX(), element.getY());
	}

	public void setElement(Element element) {
		this.world.setElement(element, element.getX(), element.getY());
	}

	/* */

	public Game(SimpleWorld world) {
		this.world = world;
	}

	public List<Monster> getMonsters() {
		return monsters;
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public World getWorld() {
		return world;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	public List<LightSource> getLightSources() {
		return lightSources;
	}

}
