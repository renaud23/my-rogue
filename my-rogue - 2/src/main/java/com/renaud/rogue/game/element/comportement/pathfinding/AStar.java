package com.renaud.rogue.game.element.comportement.pathfinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import com.renaud.rogue.game.element.Joueur;
import com.renaud.rogue.game.sequence.Game;
import com.renaud.rogue.game.tools.Point;
import com.renaud.rogue.game.world.TileDungeon;
import com.renaud.rogue.game.world.World;

public class AStar implements PathFinding {

	private Point goal;
	private Point start;
	private Game game;
	private Map<Point, Point> cameFrom = new HashMap<>();

	public AStar(Game game, Point start, Point goal) {
		this.game = game;
		this.start = start;
		this.goal = goal;
	}

	private void compute() {
		// Dungeon d2 = game.getWorld().getDungeon().clone();
		// d2.setTile(start.x, start.y, TileDungeon.Factory.createDoor());
		// d2.setTile(goal.x, goal.y, TileDungeon.Factory.createFack());

		Map<Point, Integer> costSofar = new HashMap<>();
		PriorityQueue<PointPriority> frontier = new PriorityQueue<>();
		frontier.add(new PointPriority(start, 0));
		cameFrom.put(start, null);
		costSofar.put(start, 0);

		while (!frontier.isEmpty()) {
			Point current = frontier.poll().p;
			if (current.equals(goal)) {
				break;
			}
			for (Point next : getNeighbors(game, current)) {
				int newCost = costSofar.get(current) + 1;// ;
				if (!costSofar.containsKey(next) || newCost < costSofar.get(next)) {
					costSofar.put(next, newCost);
					int priority = newCost + Math.abs(next.x - goal.x) + Math.abs(next.y - goal.y);
					frontier.add(new PointPriority(next, priority));
					cameFrom.put(next, current);
					// d2.setTile(next.x, next.y, TileDungeon.Factory.getUnknow());
				}
			}
			// d2.print(System.out, true);
		}

	}

	public List<Point> getPath() {
		compute();
		List<Point> path = new ArrayList<>();
		Point current = goal;
		while (current != null && !current.equals(start)) {
			path.add(current);
			current = cameFrom.get(current);
		}
		path.add(start);
		Collections.reverse(path);

		return path;
	}

	private List<Point> getNeighbors(Game game, Point curr) {
		List<Point> ng = new ArrayList<>();
		if (isPlayerPos(game.getJoueur(), curr.x + 1, curr.y) || game.getWorld().getTile(curr.x + 1, curr.y).canWalkOn()) {
			ng.add(new Point(curr.x + 1, curr.y));
		}
		if (isPlayerPos(game.getJoueur(), curr.x - 1, curr.y) || game.getWorld().getTile(curr.x - 1, curr.y).canWalkOn()) {
			ng.add(new Point(curr.x - 1, curr.y));
		}
		if (isPlayerPos(game.getJoueur(), curr.x, curr.y + 1) || game.getWorld().getTile(curr.x, curr.y + 1).canWalkOn()) {
			ng.add(new Point(curr.x, curr.y + 1));
		}
		if (isPlayerPos(game.getJoueur(), curr.x, curr.y - 1) || game.getWorld().getTile(curr.x, curr.y - 1).canWalkOn()) {
			ng.add(new Point(curr.x, curr.y - 1));
		}
		return ng;
	}

	private boolean isPlayerPos(Joueur j, int x, int y) {
		return j.getX() == x && j.getY() == y;
	}

	public static void main(String[] args) {
		World world = new World(120, 60);
		Point start = world.peekEmptyPlace();
		Point end = world.peekEmptyPlace();
		Game game = new Game(world, new Joueur(end.x, end.y, 120, 60));

		AStar astar = new AStar(game, start, end);
		List<Point> pathAstar = astar.getPath();

		for (Point p : pathAstar) {
			world.setTile(p.x, p.y, TileDungeon.Factory.createFack());
		}
		world.setTile(start.x, start.y, TileDungeon.Factory.createDoor());
		world.setTile(end.x, end.y, TileDungeon.Factory.getUnknow());
		world.print(System.out, false);
	}

	public static class PointPriority implements Comparable<PointPriority> {

		public Point p;
		public int priority;

		public PointPriority(Point p, int priority) {
			this.p = p;
			this.priority = priority;
		}

		public boolean equals(Object o) {
			if (o instanceof PointPriority) {
				return p.equals(((PointPriority) o).p);
			}
			return false;

		}

		public int hascCode() {
			return p.hashCode();
		}

		@Override
		public int compareTo(PointPriority o) {
			if (priority > o.priority)
				return 1;
			else if (priority < o.priority)
				return -1;
			return 0;
		}
	}
}
