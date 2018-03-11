package com.renaud.ascii.view.drawer;

import java.awt.Color;

import com.renaud.ascii.event.PlayerActionGestionnaire;
import com.renaud.ascii.view.DrawOperationAware;
import com.renaud.ascii.view.IDrawOperation;
import com.renaud.ascii.world.World;

public class PlayerActionDrawer implements DrawOperationAware {

	private World world;
	private IDrawOperation op;
	PlayerActionGestionnaire playerAction;

	public PlayerActionDrawer(World world, PlayerActionGestionnaire playerAction) {
		this.world = world;
		this.playerAction = playerAction;
	}

	@Override
	public void setDrawOperation(IDrawOperation op) {
		this.op = op;
	}

	public void draw(int startX, int startY, int carrSize) {
		if (playerAction.isAiming()) {
			// Cercle c = new Cercle(new Point(world.getJoueur().getX(),
			// world.getJoueur().getY()),
			// world.getJoueur().getWeapon().getDepht());
			// for (Point p : c.getPoints()) {
			// op.fillRect(Color.magenta, (p.getX() - startX) * carrSize, (p.getY() -
			// startY) * carrSize, carrSize,
			// carrSize, 0.5f);
			// }
			int dx = playerAction.getAimX() - startX;
			int dy = playerAction.getAimY() - startY;
			op.fillRect(Color.magenta, dx * carrSize, dy * carrSize, carrSize, carrSize, 0.5f);
		}
	}

}
