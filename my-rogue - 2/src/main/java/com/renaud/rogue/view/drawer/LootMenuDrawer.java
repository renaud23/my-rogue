package com.renaud.rogue.view.drawer;

import com.renaud.rogue.layout.loot.LootLayout;
import com.renaud.rogue.view.IDrawOperation;
import com.renaud.rogue.view.drawer.MainDrawer.Draw;

public class LootMenuDrawer implements Draw {

	private LayoutDrawer drawer;
	private IDrawOperation op;
	private LootLayout layout;

	public LootMenuDrawer(LootLayout layout, int screenLargeur, int screenHauteur) {
		this.layout = layout;
		drawer = new LayoutDrawer(layout, screenLargeur, screenHauteur);
	}

	@Override
	public void draw() {
		drawer.draw();

		this.op.drawImage(drawer.getImage(), 0, 0, 0, 0, 0, 1.0, 1.0f);
		this.op.drawImage(this.layout.getConsole().getImage(), layout.getxConsole(), layout.getyConsole(), 0, 0, 0, 1.0, 1.0f);
	}

	@Override
	public void setDrawOperation(IDrawOperation op) {
		this.op = op;
	}

}
