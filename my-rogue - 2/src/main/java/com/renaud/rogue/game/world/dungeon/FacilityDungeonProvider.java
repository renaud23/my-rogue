package com.renaud.rogue.game.world.dungeon;

import java.awt.Color;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import com.renaud.rogue.game.tools.Rectangle;
import com.renaud.rogue.game.world.TileDungeon;
import com.renaud.rogue.view.JImageBuffer;

public class FacilityDungeonProvider {

	private Random rnd = new Random();
	private Dungeon e;
	private int largeur;
	private int hauteur;

	private FacilityDungeonProvider(int largeur, int hauteur) {
		e = new Dungeon(largeur, hauteur);
		e.fill(TileDungeon.FLOOR);
		this.largeur = largeur;
		this.hauteur = hauteur;
	}

	private void init() {
		Random rnd = new Random();
		for (int i = 1; i < (e.getWidth() - 1); i++) {
			for (int j = 1; j < (e.getHeight() - 1); j++) {
				if (rnd.nextInt(100) > 45) {
					e.setTile(i, j, TileDungeon.Factory.getFloor());
				}
			}
		}
	}

	public void build() {
		init();

	}

	public void divide(int step) {
		List<TupleRect> rect = new ArrayList<>();
		Rectangle rootZone = new Rectangle(0, 0, e.getWidth(), e.getHeight());
		TupleRect root = new TupleRect(rootZone);
		rect.add(new TupleRect(rootZone));

		while (step > 0) {
			step--;
			List<TupleRect> newRect = new ArrayList<>();
			while (!rect.isEmpty()) {
				TupleRect tuple = rect.remove(0);
				Rectangle r = tuple.node;
				TupleRect a = null, b = null;
				if (r.width > r.height && r.width > 3) {
					int how = Math.max(1, r.width / 4 + rnd.nextInt(r.width / 2));
					a = new TupleRect(new Rectangle(r.x, r.y, how, r.height));
					b = new TupleRect(new Rectangle(r.x + how, r.y, r.width - how, r.height));
				} else if (r.height > 3) {
					int how = Math.max(1, r.height / 4 + rnd.nextInt(r.height / 2));
					a = new TupleRect(new Rectangle(r.x, r.y, r.width, how));
					b = new TupleRect(new Rectangle(r.x, r.y + how, r.width, r.height - how));
				}
				if (a != null && b != null) {
					newRect.add(a);
					newRect.add(b);
					tuple.left = a;
					tuple.right = b;
				}

			}
			rect = newRect;
		}

		//
		JImageBuffer buffer = new JImageBuffer(Color.white, largeur + 1, hauteur + 1);
		for (TupleRect r : rect) {
			buffer.drawRect(Color.red, r.node.x, r.node.y, r.node.width, r.node.height);
		}

		File outputfile = new File("d:/saved.png");
		try {
			VolatileImage vi = (VolatileImage) buffer.getImage();
			ImageIO.write(vi.getSnapshot(), "png", outputfile);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void check(TupleRect tuple) {

	}

	private Rectangle carveRoom(Rectangle r) {
		int x = rnd.nextInt(r.width / 4);
		int l = Math.max(3, r.width / 2) + rnd.nextInt(r.width / 4);
		int y = rnd.nextInt(r.height / 4);
		int h = Math.max(3, r.height / 2) + rnd.nextInt(r.height / 4);

		return new Rectangle(r.x + 2 + x, r.y + 2 + y, l - 3, h - 3);
	}

	public Dungeon getDungeon() {
		return e;
	}

	/* ******************************* */
	public static Builder newInstance(int largeur, int hauteur) {
		return new Builder(largeur, hauteur);
	}

	public static class Builder {

		FacilityDungeonProvider e;

		private Builder(int largeur, int hauteur) {
			e = new FacilityDungeonProvider(largeur, hauteur);
		}

		public Builder divide(int step) {
			e.divide(step);
			return this;
		}

		public Dungeon build() {
			return e.getDungeon();
		}
	}

	public final static void main(String[] args) {
		Dungeon e = FacilityDungeonProvider.newInstance(300, 300).divide(4).build();
		e.print(System.out, false);
	}

	/* **** */
	public static class TupleRect {

		public Rectangle node;
		public TupleRect left;
		public TupleRect right;

		public TupleRect(Rectangle node) {
			this.node = node;
		}

		public TupleRect() {}

	}

}
