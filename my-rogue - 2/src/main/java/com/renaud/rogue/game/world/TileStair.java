package com.renaud.rogue.game.world;

import com.renaud.rogue.view.drawer.tile.RogueTile;
import com.renaud.rogue.view.drawer.tile.StairsDownTile;
import com.renaud.rogue.view.drawer.tile.StairsUpAndDownTile;
import com.renaud.rogue.view.drawer.tile.StairsUpTile;

public class TileStair extends TileDungeon {

	private static RogueTile stairsUp = new StairsUpTile();
	private static RogueTile stairsUpAndDown = new StairsUpAndDownTile();
	private static RogueTile stairsDown = new StairsDownTile();

	private boolean up;
	private boolean down;

	public TileStair(long code, char tile, int color, boolean up, boolean down) {
		super(code, tile, color);
		this.up = up;
		this.down = down;
	}

	@Override
	public RogueTile getTile() {
		if (up && down) {
			return stairsUpAndDown;
		}
		if (up) {
			return stairsUp;
		}
		return stairsDown;
	}
}
