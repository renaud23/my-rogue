package com.renaud.rogue.view.drawer.menu;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.renaud.rogue.event.KeyboardEvent;
import com.renaud.rogue.view.DrawOperationAware;
import com.renaud.rogue.view.IDrawOperation;
import com.renaud.rogue.view.IDrawable;

public class Layout implements IDrawable, KeyboardEvent, DrawOperationAware {

	private List<Layout> children = new ArrayList<>();
	private IDrawOperation op;
	private Rectangle rectangle;
	private boolean change = true;

	public Layout(int x, int y, int largeur, int hauteur) {
		rectangle = new Rectangle(x, y, largeur, hauteur);

	}

	@Override
	public void draw() {

		// if (change) {
		change = false;
		this.op.fillRect(Color.red, rectangle.x, rectangle.y, rectangle.width, rectangle.height, 1.0f);
		this.op.drawRect(Color.yellow, rectangle.x, rectangle.y, rectangle.width, rectangle.height);

		if (!isLeaf()) {
			children.forEach(l -> l.draw());
		}

	}

	public boolean isLeaf() {
		return children.isEmpty();
	}

	@Override
	public void keyUpPressed() {}

	@Override
	public void keyDownPressed() {}

	@Override
	public void keyLeftPressed() {}

	@Override
	public void keyRightPressed() {}

	@Override
	public void setDrawOperation(IDrawOperation op) {
		this.op = op;
	}

	public void addChild(Layout child) {
		this.children.add(child);
	}

}
