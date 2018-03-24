package com.renaud.rogue.view.drawer;

import com.renaud.rogue.view.IDrawOperation;
import com.renaud.rogue.view.drawer.MainDrawer.Draw;

public class PlayingDrawer implements Draw {

    private GameDrawer gameDrawer;
    private HudDrawer hudDrawer;
    private MinimapDrawer minimapDrawer;
    private GameConsoleDrawer consoleDrawer;

    public PlayingDrawer(GameDrawer gameDrawer, HudDrawer hudDrawer, MinimapDrawer minimapDrawer,
	    GameConsoleDrawer consoleDrawer) {
	this.gameDrawer = gameDrawer;
	this.hudDrawer = hudDrawer;
	this.minimapDrawer = minimapDrawer;
	this.consoleDrawer = consoleDrawer;
    }

    @Override
    public void draw() {
	gameDrawer.draw();
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
    }
}
