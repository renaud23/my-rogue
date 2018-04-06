package com.renaud.rogue.game.element.comportement.pathfinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.renaud.rogue.game.element.Element;
import com.renaud.rogue.game.element.comportement.Comportement;
import com.renaud.rogue.game.element.monster.Bat;
import com.renaud.rogue.game.tools.Point;
import com.renaud.rogue.game.world.Game;
import com.renaud.rogue.game.world.TileDungeon;
import com.renaud.rogue.game.world.World;

public class Dijkstra implements Comportement {

    private Element element;
    private Point goal;
    private Point start;
    private Map<Point, Point> cameFrom = new HashMap<>();

    public Dijkstra(Element element, Point goal) {
	this.element = element;
	start = new Point(element.getX(), element.getY());
	this.goal = goal;
    }

    @Override
    public void activate(Game game) {
	List<Point> stack = new ArrayList<>();
	stack.add(start);
	cameFrom.put(start, null);

	while (!stack.isEmpty()) {
	    Point current = stack.remove(0);
	    if (current.equals(goal)) {
		break;
	    }
	    for (Point next : getNeighbors(game, current)) {
		if (!cameFrom.containsKey(next)) {
		    stack.add(next);
		    cameFrom.put(next, current);
		}
	    }
	}

    }

    public List<Point> getPath() {
	List<Point> path = new ArrayList<>();
	Point current = goal;
	while (!current.equals(start)) {
	    path.add(current);
	    current = cameFrom.get(current);
	}
	path.add(start);
	Collections.reverse(path);

	return path;
    }

    private List<Point> getNeighbors(Game game, Point curr) {
	List<Point> ng = new ArrayList<>();
	if (game.getWorld().getTile(curr.x + 1, curr.y).canWalkOn()) {
	    ng.add(new Point(curr.x + 1, curr.y));
	}
	if (game.getWorld().getTile(curr.x - 1, curr.y).canWalkOn()) {
	    ng.add(new Point(curr.x - 1, curr.y));
	}
	if (game.getWorld().getTile(curr.x, curr.y + 1).canWalkOn()) {
	    ng.add(new Point(curr.x, curr.y + 1));
	}
	if (game.getWorld().getTile(curr.x, curr.y - 1).canWalkOn()) {
	    ng.add(new Point(curr.x, curr.y - 1));
	}
	return ng;
    }

    @Override
    public boolean isFinished() {
	return true;
    }

    @Override
    public void reset() {
    }

    public static void main(String[] args) {
	World world = new World(120, 60);
	Point start = world.peekEmptyPlace();
	Point end = world.peekEmptyPlace();
	Game game = new Game(world);
	Element wolf = new Bat(start.x, start.y);

	Dijkstra dijkStra = new Dijkstra(wolf, end);
	dijkStra.activate(game);
	List<Point> path = dijkStra.getPath();

	for (Point p : path) {
	    world.setTile(p.x, p.y, TileDungeon.Factory.createFack());
	}
	world.setTile(start.x, start.y, TileDungeon.Factory.createDoor());
	world.setTile(end.x, end.y, TileDungeon.Factory.getUnknow());
	world.print(System.out, false);

	// System.out.println(start);
	// System.out.println(end);
	// System.out.println(path);

    }
}
