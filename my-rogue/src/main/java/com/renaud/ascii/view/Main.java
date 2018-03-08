package com.renaud.ascii.view;

import com.renaud.ascii.element.Joueur;
import com.renaud.ascii.world.JoueurMapDrawer;
import com.renaud.ascii.world.JoueurViewer;
import com.renaud.ascii.world.World;

public class Main {

	public final static void main(String[] args) {
		int screenLargeur = 800;
		int screenHauteur = 600;
		int wolrdLargeur = 160;
		int worldHauteur = 160;

		Joueur joueur = new Joueur(wolrdLargeur, worldHauteur);
		World world = new World(joueur, wolrdLargeur, worldHauteur);

		JoueurMapDrawer map = new JoueurMapDrawer(world, joueur, 10, 10, wolrdLargeur, worldHauteur);

		JoueurViewer worldViewer = new JoueurViewer(world, joueur, 40, 30, screenLargeur, screenHauteur);

		Fenetre fenetre = new Fenetre(world, screenLargeur, screenHauteur, "Ascii");
		fenetre.addDrawable(worldViewer);
		fenetre.addDrawable(map);

		EventListener kl = new EventListener();
		kl.addListener(world);
		fenetre.addKeyListener(kl);
		fenetre.addMouseMotionListener(kl);
		fenetre.start();
	}
}