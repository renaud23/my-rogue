package com.renaud.rogue.view;

import com.renaud.rogue.drawer.MainDrawer;
import com.renaud.rogue.drawer.MinimapDrawer;
import com.renaud.rogue.element.Joueur;
import com.renaud.rogue.event.EventListener;
import com.renaud.rogue.game.GameSequence;
import com.renaud.rogue.game.MainSequence;
import com.renaud.rogue.game.RogueSequence;
import com.renaud.rogue.tools.Point;
import com.renaud.rogue.world.World;

public class Main {

	public final static void main(String[] args) {
		int screenLargeur = 800;
		int screenHauteur = 600;
		int wolrdLargeur = 81;
		int worldHauteur = 81;
		int viewLargeur = 40;
		int viewHauteur = 30;

		World world = new World(wolrdLargeur, worldHauteur);
		Point start = world.peekEmptyPlace();
		Joueur joueur = new Joueur(start.x, start.y, wolrdLargeur, worldHauteur);
		GameSequence game = new GameSequence(world, joueur);

		RogueSequence mainSequence = new MainSequence(game);
		EventListener listener = new EventListener(mainSequence);

		Fenetre fenetre = new Fenetre(mainSequence, screenLargeur, screenHauteur, "Ascii");
		fenetre.addKeyListener(listener);
		fenetre.addDrawable(new MainDrawer(game, viewLargeur, viewHauteur, screenLargeur, screenHauteur));
		fenetre.addDrawable(new MinimapDrawer(game, 10, 10, 80, 80));

		fenetre.start();
	}
}