package com.renaud.rogue.view.drawer;

import com.renaud.rogue.game.sequence.Game;
import com.renaud.rogue.game.sequence.SequenceAutomate;
import com.renaud.rogue.view.IDrawOperation;
import com.renaud.rogue.view.drawer.MainDrawer.Draw;

public class PlayingDrawer implements Draw {

	private Game game;
	private GameDrawer gameDrawer;
	private HudDrawer hudDrawer;
	private MinimapDrawer minimapDrawer;
	private GameConsoleDrawer consoleDrawer;
	private LayoutDrawer lootDrawer;

	public PlayingDrawer(Game game, GameDrawer gameDrawer, HudDrawer hudDrawer, MinimapDrawer minimapDrawer,
		GameConsoleDrawer consoleDrawer, LayoutDrawer lootDrawer) {
		this.game = game;
		this.gameDrawer = gameDrawer;
		this.hudDrawer = hudDrawer;
		this.minimapDrawer = minimapDrawer;
		this.consoleDrawer = consoleDrawer;
		this.lootDrawer = lootDrawer;
	}

	@Override
	public void draw() {
		if (SequenceAutomate.getInstance().isOnLoot()) {
			lootDrawer.draw();
		} else {
			gameDrawer.draw();
		}
		hudDrawer.draw();
		minimapDrawer.draw();
		consoleDrawer.draw();
	}

	@Override
	public void setDrawOperation(IDrawOperation op) {
		gameDrawer.setDrawOperation(op);
		hudDrawer.setDrawOperation(op);
		minimapDrawer.setDrawOperation(op);
		consoleDrawer.setDrawOperation(op);
		lootDrawer.setDrawOperation(op);
	}
}
