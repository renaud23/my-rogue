package com.renaud.ascii.monster.element;

import com.renaud.ascii.element.Element;
import com.renaud.ascii.world.World;

public interface Monster extends Element {

	void activate(World world);

	public void addX(int x);

	public void addY(int y);

	public int getDepth();

	public int getSpeed();
}
