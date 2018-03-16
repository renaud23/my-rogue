package com.renaud.rogue.view;

/**
 * une entit� qui peut dessiner gr�ce � idraweroperation.
 * 
 * @author Renaud
 */
public interface IDrawable {

	default public boolean isChange() {
		return false;
	};

	default public void setChange() {
	};

	public void draw();
}
