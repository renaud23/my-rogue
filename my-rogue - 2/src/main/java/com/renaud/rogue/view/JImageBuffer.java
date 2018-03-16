package com.renaud.rogue.view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;

public class JImageBuffer implements IDrawOperation {

	private static GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
			.getDefaultConfiguration();

	private VolatileImage image;
	private int largeur;
	private int hauteur;
	private Color backgroundColor;

	public JImageBuffer(Color color, int largeur, int hauteur) {
		this.hauteur = hauteur;
		this.largeur = largeur;

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();

		synchronized (this) {
			this.image = gc.createCompatibleVolatileImage(this.largeur, this.hauteur, Transparency.TRANSLUCENT);
		}

		this.backgroundColor = color;

	}

	public void transparentClean() {
		Graphics2D g = image.createGraphics();

		// These commands cause the Graphics2D object to clear to (0,0,0,0).
		g.setColor(new Color(0, 0, 0, 0));
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OUT));
		g.fillRect(0, 0, image.getWidth(), image.getHeight()); // Clears the image.
	}

	public void transparentClean(Color color, float alpha) {
		Graphics2D g = image.createGraphics();

		// These commands cause the Graphics2D object to clear to (0,0,0,0).
		g.setColor(color);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		// g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OUT));
		g.fillRect(0, 0, image.getWidth(), image.getHeight()); // Clears the image.
	}

	public void transparentClean(int x, int y, int largeur, int hauteur) {
		Graphics2D g = image.createGraphics();

		// These commands cause the Graphics2D object to clear to (0,0,0,0).
		g.setColor(new Color(0, 0, 0, 0));
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OUT));
		g.fillRect(x, y, largeur, hauteur); // Clears the image.
	}

	public void clean() {
		Graphics2D graphics = this.image.createGraphics();
		graphics.setColor(this.backgroundColor);
		graphics.fillRect(0, 0, largeur, hauteur);
		graphics.dispose();
	}

	public void drawImage(Image image, double x, double y, double xRotation, double yRotation, double theta,
			double scale, float alpha) {
		Graphics2D gr = this.image.createGraphics();

		/** Dï¿½sactivation de l'anti-aliasing */
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		/** Demande de rendu rapide */
		gr.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		gr.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		gr.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		gr.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);

		gr.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

		if (this.image.contentsLost())
			this.image.validate(gc);

		//
		AffineTransform t = new AffineTransform();
		t.setToIdentity();

		t.translate(x, y);
		t.scale(scale, scale);
		gr.rotate(theta, xRotation, yRotation);

		gr.drawImage(image, t, null);
		gr.dispose();

	}

	public void drawImage(Image image, double x, double y, double xRotation, double yRotation, double theta,
			double scale, float alpha, Composite composite) {

		Graphics2D gr = this.image.createGraphics();

		/** Dï¿½sactivation de l'anti-aliasing */
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		/** Demande de rendu rapide */
		gr.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		gr.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		gr.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		gr.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);

		gr.setComposite(composite);

		if (this.image.contentsLost())
			this.image.validate(gc);

		//
		AffineTransform t = new AffineTransform();
		t.setToIdentity();

		t.translate(x, y);
		t.scale(scale, scale);
		gr.rotate(theta, xRotation, yRotation);

		gr.drawImage(image, t, null);
		gr.dispose();
	}

	public void drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
			Color bgcolor) {
		Graphics gr = this.image.getGraphics();

		gr.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, null);

		gr.dispose();

	}

	public void drawRect(Color color, int x, int y, int width, int height) {
		Graphics2D g = this.image.createGraphics();
		g.setColor(color);
		g.drawRect(x, y, width, height);
		g.dispose();
	}

	public void drawRect(Color color, int x, int y, int width, int height, float alpha) {
		Graphics2D g = this.image.createGraphics();
		g.setColor(color);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g.drawRect(x, y, width, height);
		g.dispose();
	}

	public void fillRect(Color color, int x, int y, int width, int height, float alpha) {
		Graphics2D g = this.image.createGraphics();
		g.setColor(color);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g.fillRect(x, y, width, height);
		g.dispose();
	}

	public void drawCircle(double x, double y, double rayon, Color color) {
		Graphics2D g = this.image.createGraphics();
		double v = rayon;
		v /= 2.0;
		double xi = x;
		xi -= v;
		double yi = y;
		yi -= v;

		g.setColor(color);
		Shape s = new Ellipse2D.Double(xi, yi, rayon, rayon);

		g.draw(s);
	}

	public void fillRect(Color color, double x, double y, double width, double height, float alpha) {
		Graphics2D g = this.image.createGraphics();
		/** D�sactivation de l'anti-aliasing */
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		/** Demande de rendu rapide */
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);

		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g.setColor(color);
		Shape s = new Rectangle2D.Double(x, y, width, height);

		g.fill(s);
		g.dispose();
	}

	public void drawImage(Image image, double x, double y, double xRotation, double yRotation, double theta,
			double scaleX, double scaleY, float alpha) {
		Graphics2D gr = this.image.createGraphics();

		/** Dï¿½sactivation de l'anti-aliasing */
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		/** Demande de rendu rapide */
		gr.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		gr.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		gr.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		gr.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);

		gr.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

		if (this.image.contentsLost())
			this.image.validate(gc);

		//
		AffineTransform t = new AffineTransform();
		t.setToIdentity();

		t.translate(x, y);
		t.scale(scaleX, scaleY);
		gr.rotate(theta, xRotation, yRotation);

		gr.drawImage(image, t, null);
		gr.dispose();

	}

	public void drawImage(Image image, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2) {
		Graphics2D gr = this.image.createGraphics();
		gr.drawImage(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
		gr.dispose();
	}

	public void drawLine(Color color, double x1, double y1, double x2, double y2) {
		Graphics2D gr = this.image.createGraphics();
		Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);
		gr.setColor(color);
		gr.draw(line);
		gr.dispose();
	}

	public void drawString(String text, int x, int y, Font font, Color color) {
		Graphics2D gr = this.image.createGraphics();
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		gr.setFont(font);
		gr.setColor(color);
		gr.drawString(text, x, y);

		gr.dispose();
	}

	public void drawRect(Color color, double x, double y, double width, double height) {
		Graphics2D g = this.image.createGraphics();
		g.setColor(color);
		Shape s = new Rectangle2D.Double(x, y, width, height);

		g.draw(s);
		g.dispose();
	}

	public int getHauteur() {
		return this.image.getHeight();
	}

	public int getLargeur() {
		return this.image.getWidth();
	}

	public Image getImage() {
		return this.image;
	}

	public void fillCircle(Color color, double x, double y, double rayon, float alpha) {
		Graphics2D g = this.image.createGraphics();
		g.setColor(color);

		Shape s = new Ellipse2D.Double(x, y, rayon * 2, rayon * 2);
		g.draw(s);
		g.dispose();
	}

	public void drawChar(String ch, int x, int y) {
		// TODO Auto-generated method stub

	}

	public void drawLine(Color color, int x1, int y1, int x2, int y2, float alpha) {
		// TODO Auto-generated method stub

	}

	public void fillPolygone(Color color, int[] x, int[] y, float alpha) {
		// TODO Auto-generated method stub

	}

	public void drawPolygone(Color color, int[] x, int[] y, float alpha) {
		// TODO Auto-generated method stub

	}

	public void drawPolygone(int[] x, int[] y, BufferedImage img) {
		// TODO Auto-generated method stub

	}

	public void drawChar(String ch, int x, int y, int size) {
		Graphics2D g = this.image.createGraphics();
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, size));

		g.drawString(ch, x, y);
	}

	@Override
	public void drawChar(String ch, int x, int y, int size, Color color) {
		Graphics2D g = this.image.createGraphics();
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, size));
		g.setColor(color);
		g.drawString(ch, x, y);
	}

}
