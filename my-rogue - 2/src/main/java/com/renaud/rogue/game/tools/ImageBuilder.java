package com.renaud.rogue.game.tools;

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

		File outputfile = new File("d:/saved.png");
		try {
			ImageIO.write(image, "png", outputfile);
		}
		catch (IOException e) {
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
				for (int i = 0; i < sh; i++) {
					for (int j = 0; j < sl; j++) {
						int coord = (sy + i) * l + sx + j;

						int pix = data[coord];
						neo[j + i * sl] = pix;

					}
				}
				return neo;
			}
			catch (InterruptedException e) {
				throw new RejectedExecutionException("Impossible de lire les pixels de l'image.", e);
			}

		}

		return null;
	}

	public static void main(String[] args) {
		int largeur = 16;
		int hauteur = 16;
		SimpleImageLoader loader = new SimpleImageLoader();
		Image image = loader.getImage("e:/MeteorRepository1Icons_fixed.png");

		int[] pix = ImageBuilder.loadImage(image, 0, 57, largeur, hauteur);

		for (int i = 0; i < pix.length; i++) {
			System.out.print(pix[i] + " ,");
			if (i % 16 == 15)
				System.out.println();
		}
		ImageBuilder.test(pix, largeur, hauteur);
	}

}
