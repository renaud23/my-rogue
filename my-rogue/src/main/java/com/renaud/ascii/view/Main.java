package com.renaud.ascii.view;

import com.renaud.ascii.dongeon.Level;
import com.renaud.ascii.dongeon.SmoothLevelProvider;
import com.renaud.ascii.dongeon.Tile;
import com.renaud.ascii.element.Joueur;
import com.renaud.ascii.event.PlayerActionGestionnaire;
import com.renaud.ascii.figure.Point;
import com.renaud.ascii.monster.element.Wolf;
import com.renaud.ascii.view.drawer.EffectsDrawer;
import com.renaud.ascii.view.drawer.JoueurMapDrawer;
import com.renaud.ascii.view.drawer.JoueurWorldDrawer;
import com.renaud.ascii.world.MainLoop;
import com.renaud.ascii.world.World;

public class Main {

	public final static void main(String[] args) {
		int screenLargeur = 800;
		int screenHauteur = 600;
		int wolrdLargeur = 81;
		int worldHauteur = 81;
		for (int i = 0; i < 10; i++) {

		}
		Level level = SmoothLevelProvider.newInstance(wolrdLargeur, worldHauteur).setNbStep(5).build();
		// Level level = LabyrintheLevelProvider.newInstance(wolrdLargeur,
		// worldHauteur).build();
		// Level level = DoomLevelProvider.newInstance(wolrdLargeur,
		// worldHauteur).setNbRooms(8).setCouloirSize(3) .build();
		Joueur joueur = new Joueur(wolrdLargeur, worldHauteur);
		World world = new World(level, joueur);

		for (int i = 0; i < 10; i++) {
			Point where = level.peekRandomOne(Tile.FLOOR);
			world.addElement(new Wolf(where.getX(), where.getY()));
		}

		JoueurMapDrawer map = new JoueurMapDrawer(world, joueur, 10, 10, wolrdLargeur, worldHauteur);

		PlayerActionGestionnaire actions = new PlayerActionGestionnaire(world);

		JoueurWorldDrawer worldViewer = new JoueurWorldDrawer(world, actions, 40, 30, screenLargeur, screenHauteur);

		MainLoop loop = new MainLoop(world, actions);
		Fenetre fenetre = new Fenetre(loop, screenLargeur, screenHauteur, "Ascii");
		fenetre.addDrawable(worldViewer);
		fenetre.addDrawable(map);
		fenetre.addDrawable(EffectsDrawer.getInstance());

		EventListener kl = new EventListener();
		kl.addListener(loop);
		fenetre.addKeyListener(kl);
		fenetre.addMouseMotionListener(kl);
		fenetre.start();
	}
}