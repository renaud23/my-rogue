package com.renaud.rogue.drawer;

import com.renaud.rogue.drawer.MainDrawer.Draw;
import com.renaud.rogue.view.IDrawOperation;

public class PlayingDrawer implements Draw {

	private GameDrawer gameDrawer;
	private HudDrawer hudDrawer;
	private MinimapDrawer minimapDrawer;

	public PlayingDrawer(GameDrawer gameDrawer, HudDrawer hudDrawer, MinimapDrawer minimapDrawer) {
		this.gameDrawer = gameDrawer;
		this.hudDrawer = hudDrawer;
		this.minimapDrawer = minimapDrawer;
	}

	@Override
	public void draw() {
		gameDrawer.draw();
		hudDrawer.draw();
		minimapDrawer.draw();
	}

	@Override
	public void setDrawOperation(IDrawOperation op) {
		gameDrawer.setDrawOperation(op);
		hudDrawer.setDrawOperation(op);
		minimapDrawer.setDrawOperation(op);
	}
}
