package com.renaud.ascii.view;

import com.renaud.ascii.dongeon.Level;
import com.renaud.ascii.dongeon.SmoothLevelProvider;
import com.renaud.ascii.element.Joueur;
import com.renaud.ascii.world.JoueurMapDrawer;
import com.renaud.ascii.world.JoueurViewer;
import com.renaud.ascii.world.World;

public class Main {

	public final static void main(String[] args) {
		int screenLargeur = 800;
		int screenHauteur = 600;
		int wolrdLargeur = 80;
		int worldHauteur = 80;

		Level level = SmoothLevelProvider.newInstance(wolrdLargeur, worldHauteur).setNbStep(5).build();
		// Level level = LabyrintheLevelProvider.newInstance(wolrdLargeur, worldHauteur).build();
		// Level level = SimpleLevelProvider.newInstance(wolrdLargeur, worldHauteur).setNbRooms(8).setCouloirSize(3).build();
		Joueur joueur = new Joueur(wolrdLargeur, worldHauteur);
		World world = new World(level, joueur);

		JoueurMapDrawer map = new JoueurMapDrawer(world, joueur, 10, 10, wolrdLargeur, worldHauteur);

		JoueurViewer worldViewer = new JoueurViewer(level, joueur, 40, 30, screenLargeur, screenHauteur);

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