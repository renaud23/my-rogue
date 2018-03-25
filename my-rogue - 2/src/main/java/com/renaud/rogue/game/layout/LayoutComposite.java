package com.renaud.rogue.game.layout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.renaud.rogue.game.tools.Rectangle;

public class LayoutComposite implements Iterable<Layout>, Layout, LayoutListener {

    private List<LayoutListener> listeners = new ArrayList<>();
    protected List<Layout> children = new ArrayList<>();
    protected LayoutComposite parent;
    protected Layout activeChild;
    protected LayoutComposite openedChild;
    protected int color = 0xFF0000;
    protected boolean actif;
    protected boolean changed = true;

    protected Rectangle rectangle;

    public LayoutComposite(int x, int y, int largeur, int hauteur) {
	rectangle = new Rectangle(x, y, largeur, hauteur);
	parent = null;
    }

    public LayoutComposite(int color, int x, int y, int largeur, int hauteur) {
	rectangle = new Rectangle(x, y, largeur, hauteur);
	this.color = color;
	parent = null;
    }

    public LayoutComposite(int x, int y, int largeur, int hauteur, LayoutComposite parent) {
	rectangle = new Rectangle(x, y, largeur, hauteur);
	this.parent = parent;
    }

    public LayoutComposite(int color, int x, int y, int largeur, int hauteur, LayoutComposite parent) {
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
		int limit = activeChild.getRectangle().height + l.getRectangle().height;
		int projx = Math.max(activeChild.getRectangle().x + activeChild.getRectangle().width,
			l.getRectangle().x + l.getRectangle().width)
			- Math.min(activeChild.getRectangle().x, l.getRectangle().x);
		int eccarty = activeChild.getRectangle().y - l.getRectangle().y;
		if (projx < limit && projx <= projxmin && eccarty > 0 && eccarty < eccartymin) {
		    projxmin = projx;
		    eccartymin = eccarty;
		    best = l;
		}
	    }
	    if (best != null) {
		best.setActif(true);
		activeChild.setActif(false);
		activeChild = best;
		notifyOver(best);
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
		int limit = activeChild.getRectangle().height + l.getRectangle().height;
		int projx = Math.max(activeChild.getRectangle().x + activeChild.getRectangle().width,
			l.getRectangle().x + l.getRectangle().width)
			- Math.min(activeChild.getRectangle().x, l.getRectangle().x);
		int eccarty = activeChild.getRectangle().y - l.getRectangle().y;
		if (projx < limit && projx < projxmin && eccarty < 0 && eccarty < eccartymin) {
		    projxmin = projx;
		    eccartymin = eccarty;
		    best = l;
		}
	    }
	    if (best != null) {
		best.setActif(true);
		activeChild.setActif(false);
		activeChild = best;
		notifyOver(best);
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
		int limit = activeChild.getRectangle().height + l.getRectangle().height;
		int projy = Math.max(activeChild.getRectangle().y + activeChild.getRectangle().height,
			l.getRectangle().y + l.getRectangle().height)
			- Math.min(activeChild.getRectangle().y, l.getRectangle().y);
		int eccartx = activeChild.getRectangle().x - l.getRectangle().x;
		if (projy < limit && projy <= projymin && eccartx > 0 && eccartx < eccartxmin) {
		    projymin = projy;
		    eccartxmin = eccartx;
		    best = l;
		}
	    }
	    if (best != null) {
		best.setActif(true);
		activeChild.setActif(false);
		activeChild = best;
		notifyOver(best);
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
		int limit = activeChild.getRectangle().height + l.getRectangle().height;
		int projy = Math.max(activeChild.getRectangle().y + activeChild.getRectangle().height,
			l.getRectangle().y + l.getRectangle().height)
			- Math.min(activeChild.getRectangle().y, l.getRectangle().y);
		int eccartx = activeChild.getRectangle().x - l.getRectangle().x;
		if (projy < limit && projy < projymin && eccartx < 0 && eccartx < eccartxmin) {
		    projymin = projy;
		    eccartxmin = eccartx;
		    best = l;
		}
	    }
	    if (best != null) {
		best.setActif(true);
		activeChild.setActif(false);
		activeChild = best;
		notifyOver(best);
	    }
	}
    }

    @Override
    public void weaponAction() {
	if (openedChild == null && activeChild != null && activeChild instanceof LayoutComposite) {
	    actif = false;
	    activeChild.setActif(false);
	    openedChild = (LayoutComposite) activeChild;
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
	    activeChild.setActif(false);
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
	    if (l.getRectangle().x < minx && l.getRectangle().y < miny) {
		minx = rectangle.x;
		miny = rectangle.y;
		witch = l;
	    }
	}
	witch.setActif(true);
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

    @Override
    public Rectangle getRectangle() {
	return rectangle;
    }

    @Override
    public void setActif(boolean actif) {
	this.actif = actif;
    }

    /* */
    public void addListener(LayoutListener listener) {
	this.listeners.add(listener);
    }

    public void notifyOver(Layout u) {
	for (LayoutListener l : listeners) {
	    l.over(u);
	}
    }

    @Override
    public void activate(Layout u) {
	for (LayoutListener l : listeners) {
	    l.activate(u);
	}
    }

}
