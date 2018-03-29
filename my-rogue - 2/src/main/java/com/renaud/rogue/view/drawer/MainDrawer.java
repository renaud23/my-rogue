package com.renaud.rogue.view.drawer;

import com.renaud.rogue.game.sequence.MainSequence;
import com.renaud.rogue.view.DrawOperationAware;
import com.renaud.rogue.view.IDrawOperation;
import com.renaud.rogue.view.IDrawable;

public class MainDrawer implements IDrawable, DrawOperationAware {

	private PlayingDrawer playingDrawer;
	private MainSequence mainSequence;

	public MainDrawer(PlayingDrawer playingDrawer, MainSequence mainSequence) {
		this.playingDrawer = playingDrawer;
		this.mainSequence = mainSequence;
	}

	public void setDrawOperation(IDrawOperation op) {
		this.playingDrawer.setDrawOperation(op);
	}

	@Override
	public void draw() {

		this.playingDrawer.draw();

	}

	public static interface Draw extends IDrawable, DrawOperationAware {}
}
