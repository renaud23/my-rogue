package com.renaud.rogue.drawer.sprite;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

import com.renaud.rogue.drawer.tile.RogueTile;
import com.renaud.rogue.tools.Chrono;
import com.renaud.rogue.view.JImageBuffer;

public abstract class AbstractSprite implements RogueTile {

    private Chrono speed;
    private int length;
    private int current;
    private int width;
    private int height;

    private JImageBuffer[] buffers;

    public AbstractSprite(int length, int largeur, int hauteur) {
	this.length = length;
	this.speed = new Chrono(getSpeed());
	buffers = new JImageBuffer[length];
	this.width = largeur;
	this.height = hauteur;
	for (int i = 0; i < length; i++) {
	    createImage(i);
	}
    }

    public abstract int[] getPixels(int index);

    public abstract long getSpeed();

    public abstract double getScale();

    private void createImage(int index) {
	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	for (int i = 0; i < width; i++) {
	    for (int j = 0; j < height; j++) {
		//
		image.setRGB(i, j, getPixels(index)[i + j * width]);// pixels[]);
	    }
	}
	buffers[index] = new JImageBuffer(Color.black, width, height);
	buffers[index].transparentClean();
	buffers[index].drawImage(image, 0, 0, 0, 0, 0, getScale(), 1.0f);
    }

    @Override
    public Image getImage() {
	if (speed.isEllapsed()) {
	    current++;
	    if (current == length) {
		current = 0;
	    }
	}

	return buffers[current].getImage();
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

}
