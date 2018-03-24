package com.renaud.rogue.view.drawer;

import java.awt.Color;

import com.renaud.rogue.game.layout.Layout;
import com.renaud.rogue.view.IDrawOperation;
import com.renaud.rogue.view.JImageBuffer;
import com.renaud.rogue.view.drawer.MainDrawer.Draw;

public class LayoutDrawer implements Draw {

    private Layout layout;
    private JImageBuffer buffer;
    private IDrawOperation op;

    public LayoutDrawer(Layout layout, int screenLargeur, int screenHauteur) {
	this.layout = layout;
	buffer = new JImageBuffer(Color.gray, screenLargeur, screenHauteur);
    }

    private void drawLayout(Layout l) {
	Color color = new Color(l.getColor());
	buffer.fillRect(color, l.getX(), l.getY(), l.getWidth(), l.getHeight(), 1.0f);
	buffer.drawRect(l.isActif() ? Color.yellow : color, l.getX(), l.getY(), l.getWidth(), l.getHeight(), 1.0f);
	for (Layout child : l) {
	    drawLayout(child);
	}
    }

    @Override
    public void draw() {
	if (layout.isChanged()) {
	    layout.setChanged(false);
	    this.drawLayout(layout);
	}
	this.op.drawImage(buffer.getImage(), 0, 0, 0, 0, 0, 1.0, 1.0f);
    }

    @Override
    public void setDrawOperation(IDrawOperation op) {
	this.op = op;
    }
}