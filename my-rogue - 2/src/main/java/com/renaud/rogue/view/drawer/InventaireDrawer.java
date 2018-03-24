package com.renaud.rogue.view.drawer;

import java.awt.Color;

import com.renaud.rogue.game.element.Joueur;
import com.renaud.rogue.view.IDrawOperation;
import com.renaud.rogue.view.JImageBuffer;
import com.renaud.rogue.view.drawer.MainDrawer.Draw;
import com.renaud.rogue.view.drawer.menu.ItemsListLayout;
import com.renaud.rogue.view.drawer.menu.Layout;

public class InventaireDrawer extends Layout implements Draw {

    private Joueur joueur;
    private int screenLargeur;
    private int screenHauteur;
    private JImageBuffer buffer;

    // private IDrawOperation op;
    private ItemsListLayout itemsListLayout;
    private Layout second;
    private boolean changed = true;

    public InventaireDrawer(Joueur joueur, int screenLargeur, int screenHauteur) {
	super(0, 0, screenLargeur, screenHauteur);
	this.joueur = joueur;
	this.screenLargeur = screenLargeur;
	this.screenHauteur = screenHauteur;

	this.itemsListLayout = new ItemsListLayout(10, 10, 4 + 5 * 20, 4 + 5 * 20, this);
	this.second = new Layout(120, 10, 100, 200, this);
	buffer = new JImageBuffer(Color.blue, screenLargeur, screenHauteur);

	this.addChild(this.itemsListLayout);
	this.addChild(this.second);

    }

    @Override
    public void draw() {
	if (changed) {
	    buffer.fillRect(Color.gray, 0, 0, screenLargeur, screenHauteur, 1.0f);
	    changed = false;
	    super.draw();
	    System.out.println("render");
	}
	this.op.drawImage(buffer.getImage(), 0, 0, 0, 0, 0, 1.0, 1.0f);
    }

    @Override
    public void setDrawOperation(IDrawOperation op) {
	this.op = op;
	itemsListLayout.setDrawOperation(buffer);
	second.setDrawOperation(buffer);
    }

    @Override
    public void goUpAction() {
	changed = true;
	super.goUpAction();
    }

    @Override
    public void goDownAction() {
	changed = true;
	super.goDownAction();
    }

    @Override
    public void goLeftAction() {
	changed = true;
	super.goLeftAction();
    }

    @Override
    public void goRightAction() {
	changed = true;
	super.goRightAction();
    }

    @Override
    public void weaponAction() {
	changed = true;
	super.weaponAction();
    }

    @Override
    public void annulerAction() {
	changed = true;
	super.annulerAction();
    }

    public String toString() {
	return "Main";
    }
}
