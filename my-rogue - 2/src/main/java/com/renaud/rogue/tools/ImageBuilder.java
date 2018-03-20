package com.renaud.rogue.tools;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.RejectedExecutionException;

import javax.imageio.ImageIO;

public class ImageBuilder {

    public static void test(int[] pixels, int largeur, int hauteur) {
	BufferedImage image = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_ARGB);

	for (int i = 0; i < largeur; i++) {
	    for (int j = 0; j < hauteur; j++) {
		//
		image.setRGB(i, j, pixels[i + j * largeur]);
	    }
	}

	File outputfile = new File("e:/saved.png");
	try {
	    ImageIO.write(image, "png", outputfile);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public static int[] loadImage(Image image, int sx, int sy, int sl, int sh) {
	if (image != null) {
	    int l = image.getWidth(null);
	    int h = image.getHeight(null);
	    int t = h * l;
	    int[] data = new int[t];
	    int[] neo = new int[sl * sh];

	    PixelGrabber pg = new PixelGrabber(image, 0, 0, l, h, data, 0, l);
	    try {
		pg.grabPixels();
		for (int i = 0; i < sl; i++) {
		    for (int j = 0; j < sh; j++) {
			int coord = (sy + j) * l + sx + i;

			int pix = data[coord];
			neo[i + j * sl] = pix;
			System.out.print(pix + " ,");

		    }
		    System.out.println();
		}
		return neo;
	    } catch (InterruptedException e) {
		throw new RejectedExecutionException("Impossible de lire les pixels de l'image.", e);
	    }

	}

	return null;
    }

    public static void main(String[] args) {
	int largeur = 16;
	int hauteur = 16;
	SimpleImageLoader loader = new SimpleImageLoader();
	Image image = loader.getImage("E:\\workspace\\my-rogue\\my-rogue - 2\\src\\main\\resources\\img\\Player0.png");

	int[] pix = ImageBuilder.loadImage(image, 0, 0, largeur, hauteur);
	ImageBuilder.test(pix, largeur, hauteur);
    }

}
