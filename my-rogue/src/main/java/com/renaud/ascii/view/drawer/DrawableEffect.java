package com.renaud.ascii.view.drawer;

import com.renaud.ascii.view.DrawOperationAware;
import com.renaud.ascii.view.IDrawable;

interface DrawableEffect extends IDrawable, DrawOperationAware {
	default boolean isFinished() {
		return false;
	}
}
