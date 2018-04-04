package com.renaud.rogue.view.drawer.sprite;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.renaud.rogue.game.tools.Chrono;
import com.renaud.rogue.view.JImageBuffer;
import com.renaud.rogue.view.drawer.tile.RogueTile;

public abstract class AbstractSprite implements RogueTile {

	private final static Random rnd = new Random();

	private Chrono speed;
	private int length;
	private int current;
	private int width;
	private int height;
	private boolean finished;

	public AbstractSprite(int length, int largeur, int hauteur) {
		this.length = length;
		this.speed = new Chrono(getSpeed());

		this.width = largeur;
		this.height = hauteur;

		this.current = rnd.nextInt(length);
	}

	public abstract long getSpeed();

	public abstract JImageBuffer[] getBuffers();

	public static JImageBuffer[] createBuffers(int[][] pixels, int length, int width, int height, double scale) {
		JImageBuffer[] buffers = new JImageBuffer[length];
		for (int i = 0; i < length; i++) {
			buffers[i] = createImage(i, pixels, width, height, scale);
		}

		return buffers;
	}

	private static JImageBuffer createImage(int index, int[][] pixels, int width, int height, double scale) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				image.setRGB(i, j, pixels[index][i + j * width]);// pixels[]);
			}
		}
		JImageBuffer buffer = new JImageBuffer(Color.black, width, height);
		buffer.transparentClean();
		buffer.drawImage(image, 0, 0, 0, 0, 0, scale, 1.0f);

		return buffer;
	}

	@Override
	public Image getImage() {
		if (speed.isEllapsed()) {
			finished = true;
			current++;
			if (current == length) {
				current = 0;
			}
		}

		return getBuffers()[current].getImage();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isEnd() {
		return finished;
	}

}
