package com.renaud.rogue.drawer;

import com.renaud.rogue.game.GameSequence;
import com.renaud.rogue.view.DrawOperationAware;
import com.renaud.rogue.view.IDrawOperation;
import com.renaud.rogue.view.IDrawable;

public class MainDrawer implements IDrawable, DrawOperationAware {

	private GameSequence game;
	private IDrawOperation op;
	private IDrawOperation buffer;
	private GameDrawer gameDrawer;
	private Draw currDrawable;

	public MainDrawer(GameSequence game, int viewLargeur, int viewHauteur, int screenLargeur, int screenHauteur) {
		this.game = game;
		this.gameDrawer = new GameDrawer(game, viewLargeur, viewHauteur, screenLargeur, screenHauteur);
		this.currDrawable = (Draw) gameDrawer;
	}

	public void setDrawOperation(IDrawOperation op) {
		currDrawable.setDrawOperation(op);
	}

	@Override
	public void draw() {
		currDrawable.draw();

	}

	public static interface Draw extends IDrawable, DrawOperationAware {
	}
}
