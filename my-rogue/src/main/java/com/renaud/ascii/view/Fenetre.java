package com.renaud.ascii.view;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import com.renaud.ascii.world.World;

public class Fenetre implements Iterable<IDrawable> {

	private JFrame frame;
	private int largeur = 800;
	private int hauteur = 600;

	private CanvasHwdBuffer buffer;
	private Timer timer;
	private List<IDrawable> drawables = new ArrayList<IDrawable>();

	private Image background;
	private World world;

	public Fenetre(World world, int largeur, int hauteur, String title) {
		this.world = world;
		this.frame = new JFrame(title);
		this.frame.setIgnoreRepaint(true);
		this.frame.setVisible(true);
		this.largeur = largeur;
		this.hauteur = hauteur;

		this.buffer = new CanvasHwdBuffer(largeur, hauteur);
		this.buffer.setPreferredSize(new Dimension(largeur, hauteur));
		this.frame.add((Component) this.buffer);
		this.buffer.createStrategy();

		this.frame.pack();
		this.frame.validate();

		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.frame.setResizable(true);
		this.timer = new Timer();

	}

	public void setIconImage(Image image) {
		this.frame.setIconImage(image);
	}

	public void resize(int largeur, int hauteur) {
		this.largeur = largeur;
		this.hauteur = hauteur;
	}

	public void repaint() {
		this.frame.repaint();
	}

	public int getLargeur() {
		return largeur;
	}

	public int getHauteur() {
		return hauteur;
	}

	public JFrame getFrame() {
		return frame;
	}

	public IDrawOperation getDrawOperation() {
		return this.buffer;
	}

	public BufferStrategy getStrategy() {
		return this.buffer.getBufferStrategy();
	}

	public void addMouseMotionListener(MouseMotionListener l) {
		this.buffer.addMouseMotionListener(l);

	}

	public void addKeyListener(KeyListener l) {
		this.buffer.addKeyListener(l);
	}

	public void addMouseListener(MouseListener l) {
		this.buffer.addMouseListener(l);
	}

	public void start() {

		final Fenetre f = this;
		final World w = this.world;

		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				w.compute();
				f.drawBackground();
				for (IDrawable drw : f) {
					if (drw instanceof DrawOperationAware)
						((DrawOperationAware) drw).setDrawOperation(f.getDrawOperation());
					drw.draw();
				}
				f.getStrategy().show();
			}
		};

		this.timer.schedule(task, 0, 10);
	}

	private void drawBackground() {
		if (background != null) {
			this.getDrawOperation().drawImage(background, 0, 0, largeur, hauteur, 0, 0, background.getWidth(null),
					background.getHeight(null));
		} else {
			this.getDrawOperation().clean();
		}
	}

	public Iterator<IDrawable> iterator() {
		return this.drawables.iterator();
	}

	public void addDrawable(IDrawable d) {
		this.drawables.add(d);
	}

	public Canvas getBuffer() {
		return this.buffer;
	}

	public void setBackground(Image background) {
		this.background = background;
	}

}
