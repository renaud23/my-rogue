package com.renaud.rogue.game.layout;

import com.renaud.rogue.game.tools.Rectangle;

public class LayoutLeaf implements Layout {

    private Rectangle rect;
    private int color;
    private LayoutComposite parent;

    private boolean actif;

    public LayoutLeaf(int x, int y, int width, int height, LayoutComposite parent) {
	rect = new Rectangle(x, y, width, height);
	color = 0x000050;
	this.parent = parent;
    }

    public LayoutLeaf(int color, int x, int y, int width, int height, LayoutComposite parent) {
	rect = new Rectangle(x, y, width, height);
	this.color = color;
	this.parent = parent;
    }

    @Override
    public Rectangle getRectangle() {
	return rect;
    }

    @Override
    public void setActif(boolean actif) {
	this.actif = actif;
    }

    @Override
    public boolean isActif() {
	return actif;
    }

    @Override
    public int getColor() {
	return color;
    }

    public Layout getParent() {
	return parent;
    }

    // @Override
    // public void annulerAction() {
    // if (parent != null) {
    //
    // }
    // }

}
