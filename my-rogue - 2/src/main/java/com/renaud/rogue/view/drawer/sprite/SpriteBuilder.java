package com.renaud.rogue.view.drawer.sprite;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import com.renaud.rogue.game.tools.ImageBuilder;
import com.renaud.rogue.game.tools.Point;
import com.renaud.rogue.game.tools.SimpleImageLoader;

public class SpriteBuilder {

	private Image image;
	private Point[] coord;
	private int[][] pixels;
	private int largeur;
	private int hauteur;
	private double scale;

	public SpriteBuilder(Image image, Point[] coord, int largeur, int hauteur, double scale) {
		this.image = image;
		this.coord = coord;
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.scale = scale;
	}

	public void genere() {
		pixels = new int[coord.length][largeur * hauteur];
		int index = 0;
		for (Point p : coord) {
			pixels[index++] = ImageBuilder.loadImage(image, p.x, p.y, largeur, hauteur);
		}
	}

	public void print(PrintStream out, String className) {
		out.println("package com.renaud.rogue.view.drawer.sprite;");
		out.println();
		out.println("public class " + className + " extends AbstractSprite {");
		out.println("\tpublic " + className + " () { super(pixels.length, " + largeur + ", " + hauteur + "); }");

		out.println("\t public long getSpeed() { return 20l;}");
		out.println("\tprivate static int[][] pixels = {");
		for (int i = 0; i < coord.length; i++) {
			out.println("\t\t{");
			for (int j = 0; j < largeur * hauteur; j++) {
				out.print(pixels[i][j]);
				if (j != largeur * hauteur - 1)
					out.print(", ");
			}
			out.println("\t\t}" + (i < coord.length - 1 ? " ," : ""));
		}
		out.println("\t};");
		out.println("\tprivate static JImageBuffer[] buffers = AbstractSprite.createBuffers(pixels, pixels.length, " + largeur + ", " + hauteur + ", " + scale + ");");

		out.println("\tpublic JImageBuffer[] getBuffers() { return buffers; }");

		out.println("}");
	}

	public static void main(String[] args) throws FileNotFoundException {
		FileOutputStream file = new FileOutputStream(new File("e:/BatSprite.java"));
		PrintStream stream = new PrintStream(file);
		SimpleImageLoader loader = new SimpleImageLoader();
		Image image = loader
			.getImage("e:/vampire-bat.png");

		Point[] points = new Point[14];
		int pas = 38;
		for (int i = 0; i < 7; i++) {
			points[i] = new Point(0 + i * pas, 0);
			points[13 - i] = new Point(0 + i * pas, 0);
		}

		SpriteBuilder sp = new SpriteBuilder(image, points, pas, pas, 0.5);
		sp.genere();
		sp.print(stream, "BatSprite");
	}

}
