package com.renaud.rogue.view.drawer;

import com.renaud.rogue.game.sequence.MainSequence;
import com.renaud.rogue.view.DrawOperationAware;
import com.renaud.rogue.view.IDrawOperation;
import com.renaud.rogue.view.IDrawable;

public class MainDrawer implements IDrawable, DrawOperationAware {

    private PlayingDrawer playingDrawer;
    private InventaireDrawer inventaireDrawer;
    private MainSequence mainSequence;

    public MainDrawer(PlayingDrawer playingDrawer, InventaireDrawer inventaireDrawer, MainSequence mainSequence) {
	this.playingDrawer = playingDrawer;
	this.inventaireDrawer = inventaireDrawer;
	this.mainSequence = mainSequence;
    }

    public void setDrawOperation(IDrawOperation op) {
	this.playingDrawer.setDrawOperation(op);
	this.inventaireDrawer.setDrawOperation(op);
    }

    @Override
    public void draw() {
	if (mainSequence.isOnPlay()) {
	    this.playingDrawer.draw();
	} else if (mainSequence.isOnInventaire()) {
	    this.inventaireDrawer.draw();
	}

    }

    public static interface Draw extends IDrawable, DrawOperationAware {
    }
}
