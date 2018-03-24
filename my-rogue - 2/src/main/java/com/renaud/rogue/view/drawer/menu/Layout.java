package com.renaud.rogue.view.drawer.menu;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.renaud.rogue.game.event.ActionEvent;
import com.renaud.rogue.view.DrawOperationAware;
import com.renaud.rogue.view.IDrawOperation;
import com.renaud.rogue.view.IDrawable;

public class Layout implements IDrawable, ActionEvent, DrawOperationAware {

    private List<Layout> children = new ArrayList<>();
    private Layout parent;
    private Layout activeChild;
    private Layout openedChild;
    private boolean actif;

    protected IDrawOperation op;
    private Rectangle rectangle;

    public Layout(int x, int y, int largeur, int hauteur) {
	rectangle = new Rectangle(x, y, largeur, hauteur);
	parent = null;
    }

    public Layout(int x, int y, int largeur, int hauteur, Layout parent) {
	rectangle = new Rectangle(x, y, largeur, hauteur);
	this.parent = parent;
    }

    @Override
    public void draw() {
	this.op.fillRect(Color.red, rectangle.x, rectangle.y, rectangle.width, rectangle.height, 1.0f);
	this.op.drawRect(actif ? Color.yellow : Color.black, rectangle.x, rectangle.y, rectangle.width,
		rectangle.height);

	if (!isLeaf()) {
	    children.forEach(l -> l.draw());
	}

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

    @Override
    public void setDrawOperation(IDrawOperation op) {
	this.op = op;
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

}
