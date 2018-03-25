package com.renaud.rogue.view.drawer;

import java.awt.Color;

import com.renaud.rogue.game.layout.LayoutComposite;
import com.renaud.rogue.game.layout.Layout;
import com.renaud.rogue.view.IDrawOperation;
import com.renaud.rogue.view.JImageBuffer;
import com.renaud.rogue.view.drawer.MainDrawer.Draw;

public class LayoutDrawer implements Draw {

    private LayoutComposite layout;
    private JImageBuffer buffer;
    private IDrawOperation op;

    public LayoutDrawer(LayoutComposite layout, int screenLargeur, int screenHauteur) {
	this.layout = layout;
	buffer = new JImageBuffer(Color.gray, screenLargeur, screenHauteur);
    }

    private void drawLayout(Layout l) {
	Color color = new Color(l.getColor());
	buffer.fillRect(color, l.getRectangle().x, l.getRectangle().y, l.getRectangle().width, l.getRectangle().height,
		1.0f);
	buffer.drawRect(l.isActif() ? Color.yellow : color, l.getRectangle().x, l.getRectangle().y,
		l.getRectangle().width, l.getRectangle().height, 1.0f);
	if (l instanceof LayoutComposite) {
	    for (Layout child : (LayoutComposite) l) {
		drawLayout(child);
	    }
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
