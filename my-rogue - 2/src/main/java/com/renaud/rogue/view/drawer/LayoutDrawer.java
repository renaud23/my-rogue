package com.renaud.rogue.view.drawer;

import java.awt.Color;

import com.renaud.rogue.game.inventaire.Item;
import com.renaud.rogue.layout.Layout;
import com.renaud.rogue.layout.LayoutComposite;
import com.renaud.rogue.layout.loot.GridInventoryItemLayout;
import com.renaud.rogue.layout.loot.GridItemLayout;
import com.renaud.rogue.layout.loot.item.ItemLayout;
import com.renaud.rogue.view.IDrawOperation;
import com.renaud.rogue.view.JImageBuffer;
import com.renaud.rogue.view.drawer.MainDrawer.Draw;
import com.renaud.rogue.view.drawer.tile.RogueTile;

public class LayoutDrawer implements Draw {

	private LayoutComposite layout;
	private JImageBuffer buffer;
	private IDrawOperation op;

	public LayoutDrawer(LayoutComposite layout, int screenLargeur, int screenHauteur) {
		this.layout = layout;
		buffer = new JImageBuffer(Color.gray, screenLargeur, screenHauteur);
	}

	private void drawLayout(Layout l) {
		Color color = new Color(l.getColor());
		buffer.fillRect(color, l.getRectangle().x, l.getRectangle().y, l.getRectangle().width, l.getRectangle().height, 1.0f);
		buffer.drawRect(l.isActif() ? Color.yellow : color, l.getRectangle().x, l.getRectangle().y, l.getRectangle().width, l.getRectangle().height, 1.0f);
		if (l instanceof GridInventoryItemLayout) {
			drawGridItemLayout((GridInventoryItemLayout) l);
		} else if (l instanceof GridItemLayout) {
			drawGridItemLayout((GridItemLayout) l);
		} else if (l instanceof LayoutComposite) {
			for (Layout child : (LayoutComposite) l) {
				drawLayout(child);
			}
		}
	}

	private void drawGridItemLayout(GridItemLayout l) {
		int i = 0;
		for (Layout child : l) {
			drawLayout(child);

			Item item = l.getLeaf(i % l.getGridWidth(), i / l.getGridWidth());
			if (item != null) {
				RogueTile tile = item.getTile().getTile();
				if (tile != null) {
					double scale = child.getRectangle().width / tile.getImage().getWidth(null);
					this.buffer.drawImage(tile.getImage(), //
						child.getRectangle().x + 1, //
						child.getRectangle().y + 1, //
						0, //
						0, //
						0, scale, scale, 1.0f);
				}
			}
			i++;
		}
	}

	private void drawGridItemLayout(GridInventoryItemLayout l) {
		int i = 0;
		for (Layout child : l) {
			drawLayout(child);

			ItemLayout itemLayout = l.getLeaf(i % l.getGridWidth(), i / l.getGridWidth());
			if (itemLayout != null) {
				RogueTile tile = itemLayout.getItem().getTile().getTile();
				if (tile != null) {
					double scale = child.getRectangle().width / tile.getImage().getWidth(null);
					this.buffer.drawImage(tile.getImage(), //
						child.getRectangle().x + 1, //
						child.getRectangle().y + 1, //
						0, //
						0, //
						0, scale, scale, 1.0f);
				}
			}
			i++;
		}
	}

	@Override
	public void draw() {
		if (layout.isChanged()) {
			layout.setChanged(false);
			this.drawLayout(layout);
		}
		this.op.drawImage(buffer.getImage(), 0, 0, 0, 0, 0, 1.0, 1.0f);
	}

	@Override
	public void setDrawOperation(IDrawOperation op) {
		this.op = op;
	}
}
