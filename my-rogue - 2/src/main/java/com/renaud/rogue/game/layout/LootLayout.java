package com.renaud.rogue.game.layout;

public class LootLayout extends Layout {

    private ItemsListLayout itemsListLayout;
    private Layout second;

    public LootLayout(int x, int y, int largeur, int hauteur) {
	super(x, y, largeur, hauteur);
	this.color = 0x505050;
	this.itemsListLayout = new ItemsListLayout(x + 10, y + 10, 4 + 5 * 20, 4 + 5 * 20, this);
	this.second = new Layout(x + 120, y + 10, 100, 200, this);

	this.addChild(this.itemsListLayout);
	this.addChild(this.second);

    }

}
