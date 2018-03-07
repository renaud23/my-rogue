package com.renaud.ascii.element;

import java.util.List;

import com.renaud.ascii.figure.Point;
import com.renaud.ascii.world.World;

public interface Element {

	public int getX();

	public int getY();

	public List<Point> getVisibilityPoints(World world);

}
