package com.renaud.rogue.game.inventaire;

public interface Ammunition extends Item {

	public final static int BULLET_9MM = 1;

	int getType();

	int getAmmoLeft();

	void removeAmmo(int nb);

	boolean isEmpty();

}
