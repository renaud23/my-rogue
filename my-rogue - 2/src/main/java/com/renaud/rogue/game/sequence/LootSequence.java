package com.renaud.rogue.game.sequence;

import com.renaud.rogue.game.event.ActionEvent;
import com.renaud.rogue.layout.loot.LootLayout;

public class LootSequence implements RogueSequence, ActionEvent {

    private Game game;
    private int x;
    private int y;
    private LootLayout layout;

    public LootSequence(Game game, int x, int y) {
	this.game = game;
	this.x = x;
	this.y = y;
	this.layout = game.getLootLayout();
	this.layout.initialise(game.getJoueur().getInventaire(), game.getWorld().getTile(x, y));
    }

    @Override
    public void goUpAction() {
	layout.setChanged(true);
	layout.goUpAction();
    }

    @Override
    public void goDownAction() {
	layout.setChanged(true);
	layout.goDownAction();
    }

    @Override
    public void goLeftAction() {
	layout.setChanged(true);
	layout.goLeftAction();
    }

    @Override
    public void goRightAction() {
	layout.setChanged(true);
	layout.goRightAction();
    }

    @Override
    public void weaponAction() {
	layout.setChanged(true);
	layout.weaponAction();
    }

    @Override
    public void annulerAction() {
	layout.setChanged(true);
	if (layout.isOpened()) {
	    layout.annulerAction();
	} else {
	    game.changeSequence(new PlayingSequence(game));
	}
    }

    @Override
    public void switchWeaponAction() {
	layout.setChanged(true);
	layout.switchWeaponAction();
    }

    @Override
    public void inventaireAction() {
	layout.setChanged(true);
	layout.inventaireAction();
    }

    @Override
    public void activateAction() {
	layout.setChanged(true);
	layout.activateAction();
    }

}
