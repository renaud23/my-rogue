package com.renaud.ascii.element;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.renaud.ascii.dongeon.Level;
import com.renaud.ascii.dongeon.Tile;
import com.renaud.ascii.figure.Cercle;
import com.renaud.ascii.figure.Point;
import com.renaud.ascii.figure.Segment;

public class ChampVision {

	private Element joueur;

	private int longueur;
	private double angleVue = 2.0 * Math.PI / 3.0;
	private double theta = Math.PI / 4;

	public ChampVision(Element joueur, int longueur, double theta) {
		this.joueur = joueur;
		this.longueur = longueur;
		this.theta = theta;
	}

	public List<Point> getPoints(Level level) {
		Set<Point> points = new HashSet<>();
		Point a = new Point(joueur.getX(), joueur.getY());

		Cercle cercle = new Cercle(a, longueur);
		for (int i = 0; i < (level.getLargeur() * level.getHauteur()); i++) {
			int xi = i % level.getLargeur();
			int yi = i / level.getLargeur();
			Point p = new Point(xi, yi);
			if (cercle.isIn(p)) {
				Segment seg = new Segment(a, p);
				boolean is = true;
				for (Point w : seg.getPoints()) {
					if (!w.equals(p)) {
						int tile = level.getTile(w.getX(), w.getY());
						if (tile == Tile.WALL) {

							is = false;
							break;
						}
					}
				}

				if (is) {
					points.add(p);
				}
			}

		}

		return new ArrayList<>(points);
	}

	public void addAngle(double alpha) {
		angleVue += alpha;
	}

	public double getAngleVue() {
		return angleVue;
	}

	public void setAngleVue(double angleVue) {
		this.angleVue = angleVue;
	}

	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}

}
