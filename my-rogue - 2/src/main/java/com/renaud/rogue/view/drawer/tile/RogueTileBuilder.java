package com.renaud.rogue.view.drawer.tile;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

import com.renaud.rogue.view.JImageBuffer;

public abstract class RogueTileBuilder implements RogueTile {

	private int largeur = 16;
	private int hauteur = 16;

	private JImageBuffer buffer;

	public RogueTileBuilder(int largeur, int hauteur) {
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.buffer = new JImageBuffer(Color.black, largeur, hauteur);
		this.buffer.transparentClean();
		createImage();
	}

	private void createImage() {
		BufferedImage image = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_ARGB);

		for (int i = 0; i < largeur; i++) {
			for (int j = 0; j < hauteur; j++) {
				//
				image.setRGB(i, j, getPixels()[i + j * largeur]);// pixels[]);
			}
		}
		buffer.drawImage(image, 0, 0, 0, 0, 0, 1.0, 1.0f);
	}

	public abstract int[] getPixels();

	public Image getImage() {
		return buffer.getImage();
	}
}
