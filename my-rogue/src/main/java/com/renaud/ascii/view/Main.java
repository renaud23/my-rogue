package com.renaud.ascii.view;

import com.renaud.ascii.element.Joueur;
import com.renaud.ascii.world.JoueurViewer;
import com.renaud.ascii.world.World;

public class Main {

	public final static void main(String[] args) {
		int screenLargeur = 800;
		int screenHauteur = 600;
		int wolrdLargeur = 500;
		int worldHauteur = 500;

		Joueur joueur = new Joueur(wolrdLargeur, worldHauteur);
		World world = new World(joueur, wolrdLargeur, worldHauteur);

		JoueurViewer worldViewer = new JoueurViewer(world, joueur, 120, 90, screenLargeur, screenHauteur);

		Fenetre fenetre = new Fenetre(world, screenLargeur, screenHauteur, "Ascii");
		fenetre.addDrawable(worldViewer);

		EventListener kl = new EventListener();
		kl.addListener(world);
		fenetre.addKeyListener(kl);
		fenetre.addMouseMotionListener(kl);
		fenetre.start();
	}
}