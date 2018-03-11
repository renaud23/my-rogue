package com.renaud.ascii.view.drawer;

import java.util.ArrayList;
import java.util.List;

import com.renaud.ascii.view.DrawOperationAware;
import com.renaud.ascii.view.IDrawOperation;
import com.renaud.ascii.view.IDrawable;

public class EffectsDrawer implements IDrawable, DrawOperationAware {
	private static EffectsDrawer instance;
	private IDrawOperation op;
	private List<DrawableEffect> drawables;

	private EffectsDrawer() {
		this.drawables = new ArrayList<>();
	}

	public static EffectsDrawer getInstance() {
		if (instance == null) {
			instance = new EffectsDrawer();
		}
		return instance;
	}

	@Override
	public void setDrawOperation(IDrawOperation op) {
		this.op = op;
	}

	@Override
	public void draw() {
		drawables.removeIf(DrawableEffect::isFinished);
		for (DrawableEffect dr : drawables) {
			dr.setDrawOperation(op);
			dr.draw();
		}

	}
}
