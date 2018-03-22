package com.renaud.rogue.drawer.sprite;

import java.awt.Image;
import java.io.PrintStream;

import com.renaud.rogue.tools.ImageBuilder;
import com.renaud.rogue.tools.Point;
import com.renaud.rogue.tools.SimpleImageLoader;

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
		out.println("package com.renaud.rogue.drawer.sprite;");
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

	public static void main(String[] args) {
		SimpleImageLoader loader = new SimpleImageLoader();
		Image image = loader
			.getImage("C:/Users/kqhlz2/workspace_js/my-rogue/my-rogue - 2/src/main/resources/img/template.png");

		Point[] points = new Point[4];
		for (int i = 0; i < 4; i++) {
			points[i] = new Point(0 + i * 16, 0);
		}

		SpriteBuilder sp = new SpriteBuilder(image, points, 16, 16, 1.0);
		sp.genere();
		sp.print(System.out, "HeroSprite");
	}

}
