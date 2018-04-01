package com.renaud.rogue.game.world.dungeon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.renaud.rogue.game.tools.Point;
import com.renaud.rogue.game.tools.Rectangle;
import com.renaud.rogue.game.world.TileDungeon;

public class FacilityDungeonProvider {

	private Random rnd = new Random();
	private Facility e;
	private Carving crowler;
	private int largeur;
	private int hauteur;

	private FacilityDungeonProvider(int largeur, int hauteur) {
		e = new Facility(largeur, hauteur);
		e.fill(TileDungeon.WALL);
		this.largeur = largeur;
		this.hauteur = hauteur;
	}

	public void divide(int step) {
		List<TupleRect> rect = new ArrayList<>();
		Rectangle rootZone = new Rectangle(2, 2, largeur - 4, hauteur - 4);
		TupleRect root = new TupleRect(rootZone);
		rect.add(root);

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

		this.crowler = new Carving(e);
		carveRoom(root, crowler);
		carveWall();
		putDoor();

		e.setFloors(crowler.getPositions().stream().collect(Collectors.toList()));
	}

	private void carveWall() {
		Facility e2 = e.clone();
		for (int i = 0; i < largeur; i++) {
			for (int j = 0; j < hauteur; j++) {
				if (i == 0 || i == largeur - 1 || j == 0 || j == hauteur - 1) {
					e.setTile(i, j, TileDungeon.Factory.getFloor());
				} else if (e2.getTile(i, j).getCode() == TileDungeon.WALL) {
					int n = 0;
					for (int a = -1; a <= 1; a++) {
						for (int b = -1; b <= 1; b++) {
							if (a == 0 && b == 0)
								continue;
							if (e2.getTile(i + a, j + b).getCode() == TileDungeon.WALL)
								n++;
						}
					}
					if (n == 8)
						e.setTile(i, j, TileDungeon.Factory.getFloor());
				}
			}
		}
	}

	private void putDoor() {
		for (Rectangle room : crowler.getRooms()) {
			List<Point> possibilities = new ArrayList<>();
			for (int i = 0; i < room.width + 2; i++) {
				for (int j = 0; j < room.height + 2; j++) {
					if (i == 0 || i == room.width + 1 || j == 0 || j == room.height + 1) {
						if (e.getTile(room.x + i - 1, room.y + j - 1).getCode() == TileDungeon.FLOOR) {
							possibilities.add(new Point(room.x + i - 1, room.y + j - 1));
						}
					}
				}
			}
			if (possibilities.size() == 1) {
				Point door = possibilities.get(0);
				e.setTile(door.x, door.y, TileDungeon.Factory.createDoor());
			}
		}
	}

	//
	// JImageBuffer buffer = new JImageBuffer(Color.white, largeur + 1, hauteur + 1);

	// for (TupleRect r : rect) {
	// buffer.drawRect(Color.red, r.node.x, r.node.y, r.node.width, r.node.height);
	// }

	// File outputfile = new File("e:/saved.png");
	// try {
	// VolatileImage vi = (VolatileImage) buffer.getImage();
	// ImageIO.write(vi.getSnapshot(), "png", outputfile);
	// }
	// catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }

	private void carveRoom(TupleRect tuple, Crowler cr) {
		cr.crowl(tuple);
		if (tuple.left != null && tuple.right != null) {
			carveRoom(tuple.left, cr);
			carveRoom(tuple.right, cr);
		}

	}

	public Facility getDungeon() {
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

		public Facility build() {
			return e.getDungeon();
		}
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

	public static interface Crowler {

		void crowl(TupleRect tuple);
	}

	public static class Carving implements Crowler {

		private Random rnd = new Random();
		private Facility dungeon;
		private List<Rectangle> rooms = new ArrayList<>();
		private Set<Point> positions = new HashSet<>();

		public Carving(Facility dungeon) {
			this.dungeon = dungeon;
		}

		public List<Rectangle> getRooms() {
			return rooms;
		}

		public Set<Point> getPositions() {
			return positions;
		}

		@Override
		public void crowl(TupleRect tuple) {
			if (tuple.left == null && tuple.right == null) {
				int sx = rnd.nextInt(tuple.node.width / 4) + 1;
				int l = tuple.node.width - sx - rnd.nextInt(tuple.node.width / 4) - 1;
				int sy = rnd.nextInt(tuple.node.height / 4) + 1;
				int h = tuple.node.height - sy - rnd.nextInt(tuple.node.height / 4) - 1;
				rooms.add(new Rectangle(tuple.node.x + sx, tuple.node.y + sy, l, h));
				for (int i = 0; i < l; i++) {
					for (int j = 0; j < h; j++) {
						dungeon.setTile(tuple.node.x + sx + i, tuple.node.y + sy + j, TileDungeon.Factory.getFloor());
						positions.add(new Point(tuple.node.x + sx + i, tuple.node.y + sy + j));
					}
				}
			} else {
				int xl = tuple.left.node.x + tuple.left.node.width / 2;
				int yl = tuple.left.node.y + tuple.left.node.height / 2;
				int xr = tuple.right.node.x + tuple.right.node.width / 2;
				int yr = tuple.right.node.y + tuple.right.node.height / 2;
				for (int l = -1; l < 0; l++) {
					for (int i = Math.min(xl, xr); i <= Math.max(xl, xr); i++) {
						for (int j = Math.min(yl, yr); j <= Math.max(yl, yr); j++) {
							dungeon.setTile(i + l, j + l, TileDungeon.Factory.getFloor());
							positions.add(new Point(i + l, j + l));
						}
					}
				}
			}
		}

	}

	/* ** */
	public final static void main(String[] args) {
		Dungeon e = FacilityDungeonProvider.newInstance(200, 200).divide(4).build();
		e.print(System.out, false);
	}

}
