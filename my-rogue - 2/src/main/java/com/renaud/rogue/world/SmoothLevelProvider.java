package com.renaud.rogue.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.renaud.rogue.tools.MathTools;
import com.renaud.rogue.tools.Point;

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
	    List<Point> todo = new ArrayList<>();
	    todo.add(start);
	    int cx = 0, cy = 0, nb = 0;
	    while (!todo.isEmpty()) {
		Point p = todo.remove(0);

		for (int i = -1; i <= 1; i++) {
		    for (int j = -1; j <= 1; j++) {
			if (i == 0 && j == 0) {
			    e2.setTile(p.x, p.y, Tile.Factory.getGhoul());
			    nb++;
			    cx += p.x;
			    cy += p.y;
			} else if (e2.getTile(p.x + i, p.y + j).getCode() == Tile.FLOOR) {
			    e2.setTile(p.x + i, p.y + j, Tile.Factory.getGhoul());
			    todo.add(new Point(p.x + i, p.y + j));
			}
		    }
		}

	    }
	    cx /= nb;
	    cy /= nb;
	    centers.add(new Point(cx, cy));
	    start = peekFirstFloor(e2);
	}

	return centers;

    }

    private Point peekFirstFloor(Dungeon d) {
	for (int i = 0; i < d.getSize(); i++) {
	    if (d.getTile(i).getCode() == Tile.FLOOR)
		return new Point(i % d.getWidth(), i / d.getHeight());
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
	Dungeon e = SmoothLevelProvider.newInstance(40, 40).setNbStep(6).build();
	e.print(System.out);
    }
}
