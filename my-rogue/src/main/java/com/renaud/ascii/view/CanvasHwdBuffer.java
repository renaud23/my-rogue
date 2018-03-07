package com.renaud.ascii.view;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class CanvasHwdBuffer extends Canvas implements IDrawOperation {

	private BufferStrategy strategy;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int largeur;
	private int hauteur;
	private Color backgroundColor = new Color(50, 50, 50);

	public CanvasHwdBuffer(int largeur, int hauteur) {
		this.setPreferredSize(new Dimension(largeur, hauteur));
		this.setIgnoreRepaint(true);
		this.setVisible(true);

		this.largeur = largeur;
		this.hauteur = hauteur;
	}

	public void createStrategy() {
		this.createBufferStrategy(2);
		this.strategy = this.getBufferStrategy();

	}

	public Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	public void clean() {
		Graphics2D gr = (Graphics2D) this.strategy.getDrawGraphics();
		gr.setBackground(backgroundColor);
		gr.clearRect(0, 0, largeur, hauteur);

	}

	public void drawImage(Image image, double x, double y, double xRotation, double yRotation, double theta, double scale, float alpha) {
		Graphics2D gr = (Graphics2D) this.strategy.getDrawGraphics();

		/** D�sactivation de l'anti-aliasing */
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		/** Demande de rendu rapide */
		gr.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		gr.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		gr.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		gr.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);

		gr.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

		//
		AffineTransform t = new AffineTransform();
		t.setToIdentity();

		t.translate(x, y);
		t.scale(scale, scale);
		gr.rotate(theta, xRotation, yRotation);

		gr.drawImage(image, t, null);
		gr.dispose();

	}

	public void drawImage(Image image, double x, double y, double xRotation, double yRotation, double theta, double scaleX, double scaleY, float alpha) {
		Graphics2D gr = (Graphics2D) this.strategy.getDrawGraphics();

		/** D�sactivation de l'anti-aliasing */
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		/** Demande de rendu rapide */
		gr.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		gr.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		gr.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		gr.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);

		gr.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

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
		Graphics2D g = (Graphics2D) this.strategy.getDrawGraphics();

		g.drawImage(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);

		g.dispose();
	}

	public void drawRect(Color color, int x, int y, int width, int height) {
		Graphics2D g = (Graphics2D) this.strategy.getDrawGraphics();
		g.setColor(color);
		g.drawRect(x, y, width, height);
		g.dispose();
	}

	public void fillRect(Color color, double x, double y, double width, double height, float alpha) {
		Graphics2D g = (Graphics2D) this.strategy.getDrawGraphics();
		/** Desactivation de l'anti-aliasing */
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

	public void drawImage(Image image, double x, double y, double xRotation, double yRotation, double theta, double scale, float alpha, Composite composite) {

		Graphics2D gr = (Graphics2D) this.strategy.getDrawGraphics();

		/** D�sactivation de l'anti-aliasing */
		// gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		// /** Demande de rendu rapide */
		// gr.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		// gr.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		// gr.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		// gr.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);

		gr.setComposite(composite);

		//
		AffineTransform t = new AffineTransform();
		t.setToIdentity();

		t.translate(x, y);
		t.scale(scale, scale);
		gr.rotate(theta, xRotation, yRotation);

		gr.drawImage(image, t, null);
		gr.dispose();
	}

	public void drawChar(String ch, int x, int y) {
		Graphics2D gr = (Graphics2D) this.strategy.getDrawGraphics();
		gr.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

		gr.drawString(ch, x, y);
	}

	public void drawChar(String ch, int x, int y, int size) {
		Graphics2D gr = (Graphics2D) this.strategy.getDrawGraphics();
		gr.setFont(new Font(Font.MONOSPACED, Font.PLAIN, size));

		gr.drawString(ch, x, y);
	}

	public void fillPolygone(Color color, int[] x, int[] y, float alpha) {
		Graphics2D gr = (Graphics2D) this.strategy.getDrawGraphics();
		gr.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

		gr.setColor(color);
		Shape s = new Polygon(x, y, x.length);
		gr.fill(s);
	}

	public void drawPolygone(Color color, int[] x, int[] y, float alpha) {
		Graphics2D gr = (Graphics2D) this.strategy.getDrawGraphics();
		gr.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

		gr.setColor(color);
		Shape s = new Polygon(x, y, x.length);
		gr.draw(s);
	}

	public void drawPolygone(int[] x, int[] y, BufferedImage img) {
		Graphics2D gr = (Graphics2D) this.strategy.getDrawGraphics();
		// gr.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

		Rectangle r = new Rectangle(0, 0, 280, 280);
		TexturePaint tx = new TexturePaint(img, r);
		gr.setPaint(tx);

		Shape s = new Polygon(x, y, x.length);
		gr.fill(s);
	}

	public void fillCircle(Color color, double x, double y, double rayon,
		float alpha) {

		Graphics2D gr = (Graphics2D) this.strategy.getDrawGraphics();
		gr.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

		gr.setColor(color);
		Shape s = new Ellipse2D.Double(x, y, rayon * 2l, rayon * 2l);
		gr.fill(s);

	}

	public void drawLine(Color color, int x1, int y1, int x2, int y2,
		float alpha) {
		// TODO Auto-generated method stub

	}

	// @Override
	public void transparentClean() {
		// TODO Auto-generated method stub

	}
}
