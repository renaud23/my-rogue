package com.renaud.rogue.view.console;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;

import com.renaud.rogue.drawer.MainDrawer.Draw;
import com.renaud.rogue.view.IDrawOperation;
import com.renaud.rogue.view.JImageBuffer;

public class Console implements Draw {

    private Font font = new Font(Font.SANS_SERIF, Font.BOLD, 12);

    private String[] lignes = {
	    "Des milliards de morceaux de plastique et 80 000 tonnes de déchets : la gigantesque décharge qui flotte dans le Pacifique est bien plus importante qu’estimé précédemment.",
	    "La durée de l'installation des mises à jour biannuelles de Windows 10 sera réduite de 63 % par rapport à la Creators Update, qui aura été la dernière à utiliser l'ancien procédé." };

    private IDrawOperation op;
    private IDrawOperation buffer;

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
    }

    @Override
    public void draw() {
	buffer.fillRect(Color.blue, 0, 0, largeur, hauteur, 1.0f);
	drawLine(lignes[0]);
    }

    @Override
    public void setDrawOperation(IDrawOperation op) {
	this.op = op;
    }

    public void drawLine(String line) {
	boolean finished = false;
	DrawTexteOperation drawer = (DrawTexteOperation) buffer;

	int iStart = 0;
	int iEnd = line.length();
	int cury = cursory;
	while (!finished) {
	    finished = true;
	    String sub = line.substring(iStart, iEnd);
	    Rectangle bounds = drawer.getBound(sub, font, cursorx, cury);
	    if (bounds.width > largeurLigne) {
		int lc = bounds.width / sub.length() + 1;
		int nbChar = largeurLigne / lc;
		iEnd = Math.min(line.length(), iStart + nbChar);
		sub = line.substring(iStart, iEnd);
		finished = false;
		bounds = drawer.getBound(sub, font, cursorx, cury);
	    }
	    drawer.drawString(sub, cursorx, 2 * cury - bounds.y, font, Color.BLACK);
	    if (!finished) {
		iStart = iEnd;
		iEnd = line.length();
		cury += bounds.height;
	    }
	    if (iStart == line.length() || cury > hauteur) {
		finished = true;
	    }
	}
    }

    public Image getImage() {
	this.draw();
	return buffer.getImage();
    }

}
