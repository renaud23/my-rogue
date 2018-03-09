package com.renaud.ascii.element;

import java.util.List;

import com.renaud.ascii.dongeon.Level;
import com.renaud.ascii.figure.Point;

public interface Element {

	public int getX();

	public int getY();

	public List<Point> getVisibilityPoints(Level level);

}
