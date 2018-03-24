package com.renaud.rogue.game.layout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.renaud.rogue.game.event.ActionEvent;
import com.renaud.rogue.game.tools.Rectangle;

public class Layout implements ActionEvent, Iterable<Layout> {

    private List<Layout> children = new ArrayList<>();
    private Layout parent;
    private Layout activeChild;
    private Layout openedChild;
    protected int color = 0xFF0000;
    private boolean actif;
    protected boolean changed = true;

    private Rectangle rectangle;

    public Layout(int x, int y, int largeur, int hauteur) {
	rectangle = new Rectangle(x, y, largeur, hauteur);
	parent = null;
    }

    public Layout(int color, int x, int y, int largeur, int hauteur) {
	rectangle = new Rectangle(x, y, largeur, hauteur);
	this.color = color;
	parent = null;
    }

    public Layout(int x, int y, int largeur, int hauteur, Layout parent) {
	rectangle = new Rectangle(x, y, largeur, hauteur);
	this.parent = parent;
    }

    public Layout(int color, int x, int y, int largeur, int hauteur, Layout parent) {
	rectangle = new Rectangle(x, y, largeur, hauteur);
	this.parent = parent;
	this.color = color;
    }

    public boolean isLeaf() {
	return children.isEmpty();
    }

    @Override
    public void goUpAction() {
	if (openedChild != null) {
	    openedChild.goUpAction();
	} else if (activeChild == null && !children.isEmpty()) {
	    activeChild = getUperLeft();
	} else {
	    int projxmin = Integer.MAX_VALUE;
	    int eccartymin = Integer.MAX_VALUE;

	    Layout best = null;
	    for (Layout l : children) {
		if (l == activeChild)
		    continue;
		int limit = activeChild.rectangle.height + l.rectangle.height;
		int projx = Math.max(activeChild.rectangle.x + activeChild.rectangle.width,
			l.rectangle.x + l.rectangle.width) - Math.min(activeChild.rectangle.x, l.rectangle.x);
		int eccarty = activeChild.rectangle.y - l.rectangle.y;
		if (projx < limit && projx <= projxmin && eccarty > 0 && eccarty < eccartymin) {
		    projxmin = projx;
		    eccartymin = eccarty;
		    best = l;
		}
	    }
	    if (best != null) {
		best.actif = true;
		activeChild.actif = false;
		activeChild = best;

	    }
	}
    }

    @Override
    public void goDownAction() {
	if (openedChild != null) {
	    activeChild.goDownAction();
	} else if (activeChild == null && !children.isEmpty()) {
	    activeChild = getUperLeft();
	} else {
	    int projxmin = Integer.MAX_VALUE;
	    int eccartymin = Integer.MAX_VALUE;

	    Layout best = null;
	    for (Layout l : children) {
		if (l == activeChild)
		    continue;
		int limit = activeChild.rectangle.height + l.rectangle.height;
		int projx = Math.max(activeChild.rectangle.x + activeChild.rectangle.width,
			l.rectangle.x + l.rectangle.width) - Math.min(activeChild.rectangle.x, l.rectangle.x);
		int eccarty = activeChild.rectangle.y - l.rectangle.y;
		if (projx < limit && projx < projxmin && eccarty < 0 && eccarty < eccartymin) {
		    projxmin = projx;
		    eccartymin = eccarty;
		    best = l;
		}
	    }
	    if (best != null) {
		best.actif = true;
		activeChild.actif = false;
		activeChild = best;

	    }
	}
    }

    @Override
    public void goLeftAction() {
	if (openedChild != null) {
	    activeChild.goLeftAction();
	} else if (activeChild == null && !children.isEmpty()) {
	    activeChild = getUperLeft();
	} else {
	    int projymin = Integer.MAX_VALUE;
	    int eccartxmin = Integer.MAX_VALUE;

	    Layout best = null;
	    for (Layout l : children) {
		if (l == activeChild)
		    continue;
		int limit = activeChild.rectangle.height + l.rectangle.height;
		int projy = Math.max(activeChild.rectangle.y + activeChild.rectangle.height,
			l.rectangle.y + l.rectangle.height) - Math.min(activeChild.rectangle.y, l.rectangle.y);
		int eccartx = activeChild.rectangle.x - l.rectangle.x;
		if (projy < limit && projy <= projymin && eccartx > 0 && eccartx < eccartxmin) {
		    projymin = projy;
		    eccartxmin = eccartx;
		    best = l;
		}
	    }
	    if (best != null) {
		best.actif = true;
		activeChild.actif = false;
		activeChild = best;

	    }
	}
    }

    @Override
    public void goRightAction() {
	if (openedChild != null) {
	    activeChild.goRightAction();
	} else if (activeChild == null && !children.isEmpty()) {
	    activeChild = getUperLeft();
	} else {
	    int projymin = Integer.MAX_VALUE;
	    int eccartxmin = Integer.MAX_VALUE;

	    Layout best = null;
	    for (Layout l : children) {
		if (l == activeChild)
		    continue;
		int limit = activeChild.rectangle.height + l.rectangle.height;
		int projy = Math.max(activeChild.rectangle.y + activeChild.rectangle.height,
			l.rectangle.y + l.rectangle.height) - Math.min(activeChild.rectangle.y, l.rectangle.y);
		int eccartx = activeChild.rectangle.x - l.rectangle.x;
		if (projy < limit && projy < projymin && eccartx < 0 && eccartx < eccartxmin) {
		    projymin = projy;
		    eccartxmin = eccartx;
		    best = l;
		}
	    }
	    if (best != null) {
		best.actif = true;
		activeChild.actif = false;
		activeChild = best;

	    }
	}
    }

    @Override
    public void weaponAction() {
	if (openedChild == null && activeChild != null && !activeChild.isLeaf()) {
	    actif = false;
	    activeChild.actif = false;
	    openedChild = activeChild;
	    openedChild.activeChild = openedChild.getUperLeft();
	} else if (activeChild != null) {
	    activeChild.weaponAction();
	}
    }

    @Override
    public void annulerAction() {
	if (openedChild != null) {
	    openedChild.annulerAction();
	} else if (parent != null) {
	    activeChild.actif = false;
	    parent.actif = false;
	    parent.openedChild = null;
	    parent.activeChild = this;
	    actif = true;
	}
    }

    public void addChild(Layout child) {
	this.children.add(child);
    }

    /* */
    private Layout getUperLeft() {
	int minx = Integer.MAX_VALUE;
	int miny = Integer.MAX_VALUE;
	Layout witch = null;
	for (Layout l : children) {
	    if (l.rectangle.x < minx && l.rectangle.y < miny) {
		minx = rectangle.x;
		miny = rectangle.y;
		witch = l;
	    }
	}
	witch.actif = true;
	return witch;
    }

    public boolean isChanged() {
	return changed;
    }

    public void setChanged(boolean changed) {
	this.changed = changed;
    }

    @Override
    public Iterator<Layout> iterator() {
	return children.iterator();
    }

    public boolean isActif() {
	return actif;
    }

    public int getX() {
	return rectangle.x;
    }

    public int getY() {
	return rectangle.y;
    }

    public int getWidth() {
	return rectangle.width;
    }

    public int getHeight() {
	return rectangle.height;
    }

    public int getColor() {
	return color;
    }

    public void setColor(int color) {
	this.color = color;
    }

}
