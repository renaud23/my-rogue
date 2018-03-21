package com.renaud.rogue.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.renaud.rogue.tools.Point;
import com.renaud.rogue.tools.Rectangle;
import com.renaud.rogue.world.Dungeon.Crowler;

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

    }

    public void connectAllRoom() {
	Dungeon e2 = e.clone();

	Point start = peekFirstFloor(e2);
	List<List<Point>> rooms = new ArrayList<>();
	while (start != null) {
	    RoomCrowler crowler = new RoomCrowler(e2);
	    e2.crowl(start, crowler);
	    rooms.add(crowler.getRoom());
	    start = peekFirstFloor(e2);
	}

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
		    e.setTile(p.x, p.y, Tile.Factory.getWall());
		}
	    }
	}

    }

    private void lighting() {
	int step = 2;

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

    public void buildEscapeRoom(int largeur, int hauteur) {
	Random rnd = new Random();
	int posX = rnd.nextInt(e.getWidth() - largeur - 1) + 1;
	int posY = rnd.nextInt(e.getHeight() - hauteur - 1) + 1;

	for (int i = 0; i < largeur; i++) {
	    for (int j = 0; j < hauteur; j++) {
		if (i == 0 || i == largeur - 1 || j == 0 || j == hauteur - 1) {
		    e.setTile(posX + i, posY + j, Tile.Factory.getWall());
		} else {
		    e.setTile(posX + i, posY + j, Tile.Factory.getFloor());
		}
	    }
	}
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

	public Builder connectAllRoom() {
	    e.connectAllRoom();
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

	public Dungeon build() {

	    return e.getDungeon();
	}
    }

    public final static void main(String[] args) {
	Dungeon e = SmoothLevelProvider.newInstance(40, 30).setNbStep(4).carve().connectAllRoom().build();
	e.print(System.out);
    }

    public static class RoomCrowler implements Crowler {

	Dungeon e2;
	List<Point> room = new ArrayList<>();

	public RoomCrowler(Dungeon e2) {
	    this.e2 = e2;
	}

	@Override
	public void crowl(int x, int y) {
	    room.add(new Point(x, y));
	    e2.setTile(x, y, Tile.Factory.getWall());
	}

	public List<Point> getRoom() {
	    return room;
	}
    }
}
