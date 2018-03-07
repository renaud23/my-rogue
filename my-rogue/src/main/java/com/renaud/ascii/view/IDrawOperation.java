package com.renaud.ascii.view;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Image;
import java.awt.image.BufferedImage;

public interface IDrawOperation {

	Image getImage();

	void clean();

	void drawImage(Image image, double x, double y, double xRotation, double yRotation, double theta, double scale, float alpha);

	void drawImage(Image image, double x, double y, double xRotation, double yRotation, double theta, double scaleX, double scaleY, float alpha);

	void drawImage(Image image, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2);

	void drawRect(Color color, int x, int y, int width, int height);

	void fillRect(Color color, double x, double y, double width, double height, float alpha);

	void fillCircle(Color color, double x, double y, double rayon, float alpha);

	void drawImage(Image image, double x, double y, double xRotation, double yRotation, double theta, double scale, float alpha, Composite composite);

	void drawChar(String ch, int x, int y);

	public void drawChar(String ch, int x, int y, int size);

	void drawLine(Color color, int x1, int y1, int x2, int y2, float alpha);

	void fillPolygone(Color color, int[] x, int[] y, float alpha);

	void drawPolygone(Color color, int[] x, int[] y, float alpha);

	void drawPolygone(int[] x, int[] y, BufferedImage img);

	void transparentClean();
}
