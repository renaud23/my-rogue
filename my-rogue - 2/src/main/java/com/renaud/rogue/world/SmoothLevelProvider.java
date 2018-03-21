package com.renaud.rogue.world;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import com.renaud.rogue.tools.MathTools;
import com.renaud.rogue.tools.Point;
import com.renaud.rogue.tools.Rectangle;

public class SmoothLevelProvider {

	private Dungeon e;
	private int nbStep;

	private SmoothLevelProvider(int largeur, int hauteur) {
		e = new Dungeon(largeur, hauteur);
		e.fill(Tile.WALL);
	}

	public void setNbStep(int nbStep) {
		this.nbStep = nbStep;
	}

	private void init() {
		Random rnd = new Random();
		for (int i = 1; i < (e.getWidth() - 1); i++) {
			for (int j = 1; j < (e.getHeight() - 1); j++) {
				if (rnd.nextInt(100) > 45) {
					e.setTile(i, j, Tile.Factory.getFloor());
				}
			}
		}
	}

	public void build() {
		init();
		for (int i = 0; i < nbStep; i++) {
			carve();
		}
		lighting();
		List<Point> centers = findCenter();
		if (centers.size() > 1) {
			Point first = centers.remove(0);
			while (!centers.isEmpty()) {
				int best = Integer.MAX_VALUE;
				Point second = null;

				for (Point p : centers) {
					int dist = MathTools.distance(first, p);
					if (dist < best) {
						best = dist;
						second = p;
					}
				}
				connect(first, second);
				centers.remove(second);
				first = second;
			}
		}

	}

	private void lighting() {
		int step = 3;

		List<Rectangle> rect = new ArrayList<>();
		rect.add(new Rectangle(0, 0, e.getWidth(), e.getHeight()));

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
			if (mx > 0 && my > 0 && mx < e.getWidth() && my < e.getHeight()) {
				if (e.getTile(mx, my).getCode() == Tile.FLOOR)
					e.addTorche(mx, my);
			}
		}

	}

	private void connect(Point a, Point b) {
		int couloirSize = 1;
		for (int i = Math.min(a.getX(), b.getX()); i <= Math.max(a.getX(), b.getX()); i++) {
			for (int k = -couloirSize; k < couloirSize; k++) {
				e.setTile(i, a.getY() + k, Tile.Factory.getFloor());
			}
		}
		for (int j = Math.min(a.getY(), b.getY()); j <= Math.max(a.getY(), b.getY()); j++) {
			for (int k = -couloirSize; k < couloirSize; k++) {
				e.setTile(b.getX(), j + k, Tile.Factory.getFloor());
			}
		}
	}

	private List<Point> findCenter() {
		List<Point> centers = new ArrayList<>();
		Dungeon e2 = e.clone();
		Point start = peekFirstFloor(e2);

		while (start != null) {
			centers.add(checkCenter(e2, start));
			start = peekFirstFloor(e2);
		}

		return centers;
	}

	private Point checkCenter(Dungeon e2, Point start) {
		Set<Point> visited = new HashSet<>();
		Stack<Point> stack = new Stack<>();
		int cx = 0, cy = 0, n = 1;

		stack.push(start);
		visited.add(start);
		e2.setTile(start.x, start.y, Tile.Factory.getWall());
		while (stack.size() > 0) {
			Point curr = stack.pop();
			if (curr.x > 0 && curr.y > 0 && curr.x < e2.getWidth() && curr.y < e2.getHeight()) {
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						if (i == 0 && j == 0) {
							cx += curr.x;
							cy += curr.y;
							n++;
							continue;
						}
						if (i != 0 && j != 0) {
							Point p = new Point(curr.x + i, curr.y + j);
							if (e2.getTile(p.x, p.y).getCode() == Tile.FLOOR && !visited.contains(p)) {
								stack.push(p);
								visited.add(p);
								e2.setTile(p.x, p.y, Tile.Factory.getWall());
							}
						}
					}
				}
			}
		}

		return new Point(cx / n, cy / n);
	}

	private Point peekFirstFloor(Dungeon d) {
		for (int i = 0; i < d.getSize(); i++) {
			if (d.getTile(i).getCode() == Tile.FLOOR)
				return new Point(i % d.getWidth(), i / d.getWidth());
		}
		return null;
	}

	private void carve() {
		Dungeon e2 = new Dungeon(e.getWidth(), e.getHeight());
		e2.fill(Tile.WALL);
		for (int i = 2; i < (e.getWidth() - 2); i++) {
			for (int j = 2; j < (e.getHeight() - 2); j++) {
				int nb = 0;

				nb += e.getTile(i - 1, j).getCode() != Tile.WALL ? 0 : 1;
				nb += e.getTile(i + 1, j).getCode() != Tile.WALL ? 0 : 1;
				nb += e.getTile(i, j - 1).getCode() != Tile.WALL ? 0 : 1;
				nb += e.getTile(i, j + 1).getCode() != Tile.WALL ? 0 : 1;
				nb += e.getTile(i - 1, j - 1).getCode() != Tile.WALL ? 0 : 1;
				nb += e.getTile(i + 1, j + 1).getCode() != Tile.WALL ? 0 : 1;
				nb += e.getTile(i - 1, j + 1).getCode() != Tile.WALL ? 0 : 1;
				nb += e.getTile(i + 1, j - 1).getCode() != Tile.WALL ? 0 : 1;
				// http://www.roguebasin.com/index.php?title=Cellular_Automata_Method_for_Generating_Random_Cave-Like_Levels
				if (e.getTile(i, j).getCode() == Tile.WALL) {
					if (nb >= 4) {
						e2.setTile(i, j, Tile.Factory.getWall());
					} else if (nb < 2) {
						e2.setTile(i, j, Tile.Factory.getFloor());
					} else {
						e2.setTile(i, j, Tile.Factory.getFloor());
					}
				} else {
					if (nb >= 5) {
						e2.setTile(i, j, Tile.Factory.getWall());
					} else {
						e2.setTile(i, j, Tile.Factory.getFloor());
					}
				}
			}
		}
		e = e2;

	}

	public Dungeon getDungeon() {
		return e;
	}

	/* ******************************* */
	public static Builder newInstance(int largeur, int hauteur) {
		return new Builder(largeur, hauteur);
	}

	public static class Builder {

		SmoothLevelProvider e;

		private Builder(int largeur, int hauteur) {
			e = new SmoothLevelProvider(largeur, hauteur);
		}

		public Builder setNbStep(int nbStep) {
			e.nbStep = nbStep;
			return this;
		}

		public Dungeon build() {
			e.build();
			return e.getDungeon();
		}
	}

	public final static void main(String[] args) {
		Dungeon e = SmoothLevelProvider.newInstance(80, 60).setNbStep(6).build();
		e.print(System.out);
	}
}
