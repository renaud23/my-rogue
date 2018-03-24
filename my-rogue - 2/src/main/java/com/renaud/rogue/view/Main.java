package com.renaud.rogue.view;

import com.renaud.rogue.game.element.Joueur;
import com.renaud.rogue.game.event.EventListener;
import com.renaud.rogue.game.sequence.Game;
import com.renaud.rogue.game.sequence.InventaireSequence;
import com.renaud.rogue.game.sequence.MainSequence;
import com.renaud.rogue.game.tools.Point;
import com.renaud.rogue.game.world.World;
import com.renaud.rogue.view.drawer.GameConsoleDrawer;
import com.renaud.rogue.view.drawer.GameDrawer;
import com.renaud.rogue.view.drawer.HudDrawer;
import com.renaud.rogue.view.drawer.InventaireDrawer;
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

		World world = new World(wolrdLargeur, worldHauteur);
		Point start = world.peekEmptyPlace();
		Joueur joueur = new Joueur(start.x, start.y, wolrdLargeur, worldHauteur);
		Game game = new Game(world, joueur);

		InventaireDrawer inventaireDrawer = new InventaireDrawer(joueur, screenLargeur, screenHauteur);
		InventaireSequence inventaire = new InventaireSequence(game, inventaireDrawer);

		MainSequence mainSequence = new MainSequence(game, inventaire);
		EventListener listener = new EventListener(mainSequence);

		Fenetre fenetre = new Fenetre(mainSequence, screenLargeur + hudLargeur, screenHauteur + consoleHeight, "My Rogue");
		fenetre.addKeyListener(listener);

		GameDrawer gameDrawer = new GameDrawer(game, viewLargeur, viewHauteur, screenLargeur, screenHauteur);
		HudDrawer hudDrawer = new HudDrawer(joueur, screenLargeur, 0, hudLargeur, hudHauteur);
		MinimapDrawer minimapDrawer = new MinimapDrawer(game, screenLargeur, hudHauteur - mapHauteur, mapLargeur, mapHauteur);
		GameConsoleDrawer consoleDrawer = new GameConsoleDrawer(0, screenHauteur, consoleWidth, consoleHeight);
		PlayingDrawer playingDrawer = new PlayingDrawer(gameDrawer, hudDrawer, minimapDrawer, consoleDrawer);

		MainDrawer mainDrawer = new MainDrawer(playingDrawer, inventaireDrawer, mainSequence);
		fenetre.addDrawable(mainDrawer);

		fenetre.start();
	}
}