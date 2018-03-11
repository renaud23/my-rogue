package com.renaud.ascii.view.drawer;

import java.awt.Color;

import com.renaud.ascii.tools.Chrono;
import com.renaud.ascii.view.IDrawOperation;

public class ImpactEffect implements DrawableEffect {

	int x;
	int y;
	int size;
	Chrono delay;
	private IDrawOperation op;

	public ImpactEffect(int x, int y, int size, long delay) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.delay = new Chrono(delay);
	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return delay.isEllapsed();
	}

	@Override
	public void draw() {
		op.fillRect(Color.red, x, y, size, size, 1.0f);
	}

	@Override
	public void setDrawOperation(IDrawOperation op) {
		this.op = op;
	}

}
