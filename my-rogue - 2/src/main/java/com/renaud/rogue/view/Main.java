package com.renaud.rogue.view;

import com.renaud.rogue.game.element.Joueur;
import com.renaud.rogue.game.event.EventListener;
import com.renaud.rogue.game.sequence.PlayingSequence;
import com.renaud.rogue.game.sequence.SequenceAutomate;
import com.renaud.rogue.game.tools.Point;
import com.renaud.rogue.game.world.Game;
import com.renaud.rogue.game.world.LargeWorld;
import com.renaud.rogue.game.world.World;
import com.renaud.rogue.layout.loot.InventoryLayout;
import com.renaud.rogue.layout.loot.LootLayout;
import com.renaud.rogue.view.drawer.GameConsoleDrawer;
import com.renaud.rogue.view.drawer.GameDrawer;
import com.renaud.rogue.view.drawer.HudDrawer;
import com.renaud.rogue.view.drawer.LootMenuDrawer;
import com.renaud.rogue.view.drawer.MainDrawer;
import com.renaud.rogue.view.drawer.MinimapDrawer;
import com.renaud.rogue.view.drawer.PlayingDrawer;

public class Main {

	public final static void main(String[] args) {

		int wolrdLargeur = 60;
		int worldHauteur = 60;
		int viewLargeur = 40;
		int viewHauteur = 30;

		int tileSize = 16;
		int screenLargeur = 40 * tileSize;
		int screenHauteur = 30 * tileSize;
		int hudLargeur = 10 * tileSize;
		int hudHauteur = screenHauteur;
		int mapLargeur = hudLargeur;
		int mapHauteur = (wolrdLargeur / worldHauteur) * hudLargeur;
		int consoleWidth = screenLargeur + hudLargeur;
		int consoleHeight = 100;

		// game layer
		World world = new LargeWorld(wolrdLargeur, worldHauteur);
		Point start = world.peekEmptyPlace();
		Joueur joueur = new Joueur(start.x, start.y, wolrdLargeur, worldHauteur);
		Game game = new Game(world, joueur);

		// game layout
		InventoryLayout layoutInventory = new InventoryLayout(0, 0, screenLargeur, screenHauteur);
		LootLayout layoutLoot = new LootLayout(0, 0, screenLargeur, screenHauteur);

		// state sequence initialisation
		SequenceAutomate.getInstance().setInventoryLayout(layoutInventory);
		SequenceAutomate.getInstance().setLootLayout(layoutLoot);
		SequenceAutomate.getInstance().setNextSequence(new PlayingSequence(game));
		EventListener listener = new EventListener(SequenceAutomate.getInstance());

		// drawer layer
		LootMenuDrawer lootDrawer = new LootMenuDrawer(layoutLoot, screenLargeur, screenHauteur);
		GameDrawer gameDrawer = new GameDrawer(game, viewLargeur, viewHauteur, screenLargeur, screenHauteur);
		HudDrawer hudDrawer = new HudDrawer(joueur, screenLargeur, 0, hudLargeur, hudHauteur);
		MinimapDrawer minimapDrawer = new MinimapDrawer(game, screenLargeur, hudHauteur - mapHauteur, mapLargeur, mapHauteur);
		GameConsoleDrawer consoleDrawer = new GameConsoleDrawer(0, screenHauteur, consoleWidth, consoleHeight);
		PlayingDrawer playingDrawer = new PlayingDrawer(game, gameDrawer, hudDrawer, minimapDrawer, consoleDrawer, lootDrawer);
		MainDrawer mainDrawer = new MainDrawer(playingDrawer);

		// start application
		Fenetre fenetre = new Fenetre(screenLargeur + hudLargeur, screenHauteur + consoleHeight, "My Rogue");
		fenetre.addKeyListener(listener);
		fenetre.addDrawable(mainDrawer);
		fenetre.start();
	}
}