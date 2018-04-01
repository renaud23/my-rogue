package com.renaud.rogue.game.world.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.renaud.rogue.game.tools.MathTools;
import com.renaud.rogue.game.tools.Point;
import com.renaud.rogue.game.tools.Rectangle;
import com.renaud.rogue.game.world.TileDungeon;

public class SmoothDungeonProvider {

	private Cave cave;
	private int nbStep;

	private SmoothDungeonProvider(int largeur, int hauteur) {
		cave = new Cave(largeur, hauteur);
		cave.fill(TileDungeon.WALL);
	}

	public void setNbStep(int nbStep) {
		this.nbStep = nbStep;
	}

	private void init() {
		Random rnd = new Random();
		for (int i = 1; i < (cave.getWidth() - 1); i++) {
			for (int j = 1; j < (cave.getHeight() - 1); j++) {
				if (rnd.nextInt(100) > 45) {
					cave.setTile(i, j, TileDungeon.Factory.getFloor());
				}
			}
		}
	}

	public void build() {
		init();
		for (int i = 0; i < nbStep; i++) {
			carve();
		}

	}

	private List<List<Point>> getAllRooms() {
		Cave e2 = cave.clone();

		Point start = peekFirstFloor(e2);
		List<List<Point>> rooms = new ArrayList<>();
		while (start != null) {
			RoomCrowler crowler = new RoomCrowler(e2);
			e2.crowl(start, crowler);
			rooms.add(crowler.getRoom());
			start = peekFirstFloor(e2);
		}

		return rooms;
	}

	private List<Point> fillSmallest(List<List<Point>> rooms) {
		int largedSize = -1;
		List<Point> best = null;
		for (List<Point> room : rooms) {
			if (room.size() > largedSize) {
				largedSize = room.size();
				best = room;
			}
		}

		for (List<Point> room : rooms) {
			if (room != best) {
				for (Point p : room) {
					cave.setTile(p.x, p.y, TileDungeon.Factory.getWall());
				}
			}
		}

		return best;
	}

	public void fillSmallest() {
		List<List<Point>> rooms = getAllRooms();
		fillSmallest(rooms);
	}

	private void lighting() {
		int step = 2;

		List<Rectangle> rect = new ArrayList<>();
		rect.add(new Rectangle(0, 0, cave.getWidth(), cave.getHeight()));

		while (step > 0) {
			step--;
			List<Rectangle> newRect = new ArrayList<>();
			while (!rect.isEmpty()) {
				Rectangle r = rect.remove(0);
				int w = r.width / 2;

				int h = r.height / 2;

				newRect.add(new Rectangle(r.x, r.y, w, h));
				newRect.add(new Rectangle(r.x + r.width - w, r.y, r.width, h));
				newRect.add(new Rectangle(r.x, r.y + r.height - h, w, r.height - h));
				newRect.add(new Rectangle(r.x + r.width - w, r.y + r.height - h, r.width - w, r.height - h));
			}
			rect = newRect;
		}
		Random rand = new Random();
		for (Rectangle r : rect) {
			int mx = r.x + rand.nextInt(r.width);
			int my = r.y + rand.nextInt(r.height);
			if (mx > 0 && my > 0 && mx < cave.getWidth() && my < cave.getHeight()) {
				if (cave.getTile(mx, my).getCode() == TileDungeon.FLOOR)
					cave.addTorche(mx, my);
			}
		}
	}

	private Point peekFirstFloor(Cave d) {
		for (int i = 0; i < d.getSize(); i++) {
			if (d.getTile(i).getCode() == TileDungeon.FLOOR)
				return new Point(i % d.getWidth(), i / d.getWidth());
		}
		return null;
	}

	private void carve() {
		Cave e2 = new Cave(cave.getWidth(), cave.getHeight());
		e2.fill(TileDungeon.WALL);
		for (int i = 2; i < (cave.getWidth() - 2); i++) {
			for (int j = 2; j < (cave.getHeight() - 2); j++) {
				int nb = 0;

				nb += cave.getTile(i - 1, j).getCode() != TileDungeon.WALL ? 0 : 1;
				nb += cave.getTile(i + 1, j).getCode() != TileDungeon.WALL ? 0 : 1;
				nb += cave.getTile(i, j - 1).getCode() != TileDungeon.WALL ? 0 : 1;
				nb += cave.getTile(i, j + 1).getCode() != TileDungeon.WALL ? 0 : 1;
				nb += cave.getTile(i - 1, j - 1).getCode() != TileDungeon.WALL ? 0 : 1;
				nb += cave.getTile(i + 1, j + 1).getCode() != TileDungeon.WALL ? 0 : 1;
				nb += cave.getTile(i - 1, j + 1).getCode() != TileDungeon.WALL ? 0 : 1;
				nb += cave.getTile(i + 1, j - 1).getCode() != TileDungeon.WALL ? 0 : 1;
				// http://www.roguebasin.com/index.php?title=Cellular_Automata_Method_for_Generating_Random_Cave-Like_Levels
				if (cave.getTile(i, j).getCode() == TileDungeon.WALL) {
					if (nb >= 4) {
						e2.setTile(i, j, TileDungeon.Factory.getWall());
					} else if (nb < 2) {
						e2.setTile(i, j, TileDungeon.Factory.getFloor());
					} else {
						e2.setTile(i, j, TileDungeon.Factory.getFloor());
					}
				} else {
					if (nb >= 5) {
						e2.setTile(i, j, TileDungeon.Factory.getWall());
					} else {
						e2.setTile(i, j, TileDungeon.Factory.getFloor());
					}
				}
			}
		}
		cave = e2;

	}

	public Cave getDungeon() {
		return cave;
	}

	public void buildEscapeRoom(int largeur, int hauteur) {
		Random rnd = new Random();
		int posX = rnd.nextInt(cave.getWidth() - largeur - 1) + 1;
		int posY = rnd.nextInt(cave.getHeight() - hauteur - 1) + 1;

		List<Point> exit = new ArrayList<>();
		List<Point> walls = new ArrayList<>();
		for (int i = 0; i < largeur; i++) {
			for (int j = 0; j < hauteur; j++) {
				if (i == 0 || i == largeur - 1 || j == 0 || j == hauteur - 1) {
					cave.setTile(posX + i, posY + j, TileDungeon.Factory.getWall());
					walls.add(new Point(posX + i, posY + j));
				} else {
					cave.setTile(posX + i, posY + j, TileDungeon.Factory.getFloor());
					exit.add(new Point(posX + i, posY + j));
				}
			}
		}
		List<List<Point>> rooms = getAllRooms();

		for (List<Point> room : rooms) {
			if (room.containsAll(exit)) {
				exit = room;
			}
		}
		rooms.remove(exit);
		List<Point> best = fillSmallest(rooms);
		//
		rooms = getAllRooms();
		List<Point> curr = rooms.remove(0);
		Point ba = null, bb = null;
		int min = Integer.MAX_VALUE;
		while (!rooms.isEmpty()) {
			List<Point> next = rooms.remove(0);
			for (Point a : curr) {
				for (Point b : next) {
					int dist = MathTools.distance(a, b);
					if (dist < min) {
						min = dist;
						ba = a;
						bb = b;
					}
				}
			}
		}

		int dx = (ba.x + bb.x) / 2;
		int dy = (ba.y + bb.y) / 2;

		cave.setDoors(new ArrayList<>(exit));
		cave.setFloors(best);
		cave.setTile(dx, dy, TileDungeon.Factory.createDoor());

	}

	/* ******************************* */
	public static Builder newInstance(int largeur, int hauteur) {
		return new Builder(largeur, hauteur);
	}

	public static class Builder {

		SmoothDungeonProvider e;

		private Builder(int largeur, int hauteur) {
			e = new SmoothDungeonProvider(largeur, hauteur);
		}

		public Builder setNbStep(int nbStep) {
			e.nbStep = nbStep;
			return this;
		}

		public Builder fillSmallest() {
			e.fillSmallest();
			return this;
		}

		public Builder lighting() {
			e.lighting();
			return this;
		}

		public Builder buildEscapeRoom(int largeur, int hauteur) {
			e.buildEscapeRoom(largeur, hauteur);
			return this;
		}

		public Builder carve() {
			e.build();
			return this;
		}

		public Cave build() {

			return e.getDungeon();
		}
	}

	public final static void main(String[] args) {
		Cave e = SmoothDungeonProvider.newInstance(80, 60).setNbStep(4).carve().buildEscapeRoom(8, 8).build();
		e.print(System.out, false);
	}

}
