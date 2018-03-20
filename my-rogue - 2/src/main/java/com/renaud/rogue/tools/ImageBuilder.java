package com.renaud.rogue.tools;

import java.awt.Image;
import java.awt.image.PixelGrabber;
import java.util.concurrent.RejectedExecutionException;

public class ImageBuilder {

    public static void loadImage(Image image, int sx, int sy, int sl, int sh) {
	if (image != null) {
	    int l = image.getWidth(null);
	    int h = image.getHeight(null);
	    int t = h * l;
	    int[] data = new int[t];

	    PixelGrabber pg = new PixelGrabber(image, 0, 0, l, h, data, 0, l);
	    try {
		pg.grabPixels();
		for (int i = 0; i < sl; i++) {
		    for (int j = 0; j < sh; j++) {
			int coord = (sy + j) * l + sx + i;

			int pix = data[coord];
		    }
		}

		// for (int i = 0; i < t; i++) {
		// System.out.println(data[i]);
		//
		// }

	    } catch (InterruptedException e) {
		throw new RejectedExecutionException("Impossible de lire les pixels de l'image.", e);
	    }

	}
    }

    public static void main(String[] args) {
	SimpleImageLoader loader = new SimpleImageLoader();
	Image image = loader.getImage("E:\\workspace\\my-rogue\\my-rogue - 2\\src\\main\\resources\\img\\Player0.png");

	ImageBuilder.loadImage(image, 31, 47, 16, 16);
    }

}
