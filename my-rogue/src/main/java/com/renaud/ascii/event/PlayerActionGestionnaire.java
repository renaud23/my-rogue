package com.renaud.ascii.event;

import com.renaud.ascii.world.World;

public class PlayerActionGestionnaire implements OnEventAction {

	private OnEventAction mouvements;
	private WeaponGestionnaire weapons;
	private OnEventAction activeManager;

	private boolean aiming;

	public PlayerActionGestionnaire(World world) {
		mouvements = new PlayerMouvementsGestionnaire(world);
		weapons = new WeaponGestionnaire(world);

		activeManager = mouvements;
	}

	@Override
	public boolean activate() {
		if (aiming) {
			if (activeManager.isFinished()) {
				aiming = false;
				activeManager = mouvements;
				mouvements.reset();
			}
		}

		return activeManager.activate();
	}

	@Override
	public void keyUpPressed() {
		activeManager.keyUpPressed();
	}

	@Override
	public void keyUpReleased() {
		activeManager.keyUpReleased();
	}

	@Override
	public void keyDownPressed() {
		activeManager.keyDownPressed();
	}

	@Override
	public void keyDownReleased() {
		activeManager.keyDownReleased();
	}

	@Override
	public void keyLeftPressed() {
		activeManager.keyLeftPressed();
	}

	@Override
	public void keyLeftReleaseded() {
		activeManager.keyLeftReleaseded();
	}

	@Override
	public void keyRightPressed() {
		activeManager.keyRightPressed();
	}

	@Override
	public void keyRightRealesed() {
		activeManager.keyRightRealesed();
	}

	@Override
	public void spacePressed() {
		activeManager.spacePressed();

	}

	@Override
	public void spaceReleaseded() {
		if (!aiming) {
			aiming = true;
			activeManager = weapons;
			weapons.reset();
		} else {
			activeManager.spaceReleaseded();
		}
	}

	@Override
	public void mouseMoved(int x, int y, int varx, int vary) {
		activeManager.mouseMoved(x, y, varx, vary);
	}

	public boolean isAiming() {
		return aiming;
	}

	public int getAimX() {
		return weapons.getAimX();
	}

	public int getAimY() {
		return weapons.getAimY();
	}
}
