package com.renaud.rogue.view.drawer;

import com.renaud.rogue.view.DrawOperationAware;
import com.renaud.rogue.view.IDrawOperation;
import com.renaud.rogue.view.IDrawable;

public class MainDrawer implements IDrawable, DrawOperationAware {

	private PlayingDrawer playingDrawer;

	public MainDrawer(PlayingDrawer playingDrawer) {
		this.playingDrawer = playingDrawer;
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
