package com.renaud.rogue.game.layout;

import java.util.ArrayList;
import java.util.List;

import com.renaud.rogue.game.tools.Rectangle;

public class LayoutLeaf implements Layout {

    private Rectangle rect;
    private int color;
    private LayoutComposite parent;
    private List<LayoutListener> listeners = new ArrayList<>();
    private Long id;

    private boolean actif;

    public LayoutLeaf(Long id, int x, int y, int width, int height, LayoutComposite parent) {
	rect = new Rectangle(x, y, width, height);
	color = 0x000050;
	this.id = id;
	this.parent = parent;
    }

    public LayoutLeaf(Long id, int color, int x, int y, int width, int height, LayoutComposite parent) {
	rect = new Rectangle(x, y, width, height);
	this.color = color;
	this.parent = parent;
	this.id = id;
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

    @Override
    public void weaponAction() {
	for (LayoutListener l : listeners) {
	    l.activate(this);
	}
    }

    public void addListener(LayoutListener listener) {
	this.listeners.add(listener);
    }

    public Long getId() {
	return id;
    }

}
