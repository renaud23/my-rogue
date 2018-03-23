package com.renaud.rogue.view.console;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.renaud.rogue.view.IDrawOperation;
import com.renaud.rogue.view.JImageBuffer;
import com.renaud.rogue.view.drawer.MainDrawer.Draw;

public class Console implements Draw {

	private Font font = new Font(Font.SANS_SERIF, Font.BOLD, 12);

	private List<Line> lignes = new ArrayList<>();
	private int currentLine = 0;

	private IDrawOperation op;
	private IDrawOperation buffer;
	boolean hasChanged = true;
	boolean showLast = true;

	private int largeur;
	private int hauteur;
	private int largeurLigne;

	int cursorx = 5;
	int cursory = 0;

	public Console(int largeur, int hauteur) {
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.largeurLigne = largeur - 2 * cursorx;
		buffer = new JImageBuffer(Color.white, largeur, hauteur);

		lignes.add(new Line("Bonjour !", Color.blue));
		// lignes.add(new Line("Des milliards de morceaux de plastique et 80 000 tonnes de déchets : la gigantesque décharge qui flotte dans le Pacifique est bien plus importante qu’estimé
		// précédemment.", Color.red));
		// lignes.add(new Line("La durée de l'installation des mises à jour biannuelles de Windows 10 sera réduite de 63 % par rapport à la Creators Update, qui aura été la dernière à utiliser l'ancien
		// procédé.", Color.yellow));
	}

	@Override
	public void draw() {
		if (!hasChanged)
			return;
		hasChanged = false;
		buffer.fillRect(Color.black, 0, 0, largeur, hauteur, 1.0f);
		cursory = 0;
		for (int i = currentLine; i < lignes.size(); i++) {
			if (!drawLine(lignes.get(i))) {
				break;
			}
		}

	}

	@Override
	public void setDrawOperation(IDrawOperation op) {
		this.op = op;
	}

	public boolean drawLine(Line line) {
		boolean finished = false;
		DrawTexteOperation drawer = (DrawTexteOperation) buffer;

		int iStart = 0;
		int iEnd = line.line.length();
		cursory += 5;
		int cury = cursory;
		boolean noMore = false;
		while (!finished) {
			finished = true;
			String sub = line.line.substring(iStart, iEnd);
			Rectangle bounds = drawer.getBound(sub, font, cursorx, cury);
			if (bounds.width > largeurLigne) {
				int lc = bounds.width / sub.length() + 1;
				int nbChar = largeurLigne / lc;
				iEnd = Math.min(line.line.length(), iStart + nbChar);
				sub = line.line.substring(iStart, iEnd);
				finished = false;
				bounds = drawer.getBound(sub, font, cursorx, cury);
			}
			drawer.drawString(sub, cursorx, 2 * cury - bounds.y, font, line.color);
			if (!finished) {
				iStart = iEnd;
				iEnd = line.line.length();
				cury += bounds.height;
			}
			if (iStart == line.line.length() || cury > hauteur - bounds.height) {
				finished = true;
				noMore = true;
			}
			cursory += bounds.height;
		}

		if (noMore && showLast && currentLine < lignes.size() - 1) {
			currentLine++;
			hasChanged = true;
			draw();

		}

		return !noMore;
	}

	public void addLigne(String line, Color color) {
		hasChanged = true;
		lignes.add(new Line(line, color));
	}

	public Image getImage() {
		this.draw();
		return buffer.getImage();
	}

	private static class Line {

		public String line;
		public Color color;

		public Line(String line, Color color) {
			this.line = line;
			this.color = color;
		}

	}

}
