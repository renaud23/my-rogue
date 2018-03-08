package com.renaud.ascii.dongeon;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Labyrinthe {

	private final static int MUR_NORD = 128;
	private final static int MUR_SUD = 64;
	private final static int MUR_EST = 32;
	private final static int MUR_OUEST = 16;

	public final static int MUR_CODE = 1;
	public final static int MUR_COULOIR = 0;

	private int largeur;
	private int hauteur;
	private int size;

	private int[] positions;
	private List<List<Integer>> groupes;
	private List<List<Integer>> pile;

	private int[] table;
	private int largeurTable;
	private int hauteurTable;

	private int[] data;

	public Labyrinthe(int largeur, int hauteur) {
		// largeurTable = 1 + largeur * 2 ;
		// hauteurTable = 1 + hauteur * 2 ;

		this.largeur = largeur / 2;
		this.hauteur = hauteur / 2;

		this.init();
	}

	private void init() {
		this.size = largeur * hauteur;
		this.data = new int[this.size];
		this.groupes = new ArrayList<>(this.size);
		this.positions = new int[this.size];
		this.pile = new ArrayList<>();

		for (int i = 0; i < size; i++) {
			this.data[i] = 240;
			this.positions[i] = i;

			List<Integer> cg = new ArrayList<>();
			cg.add(i);
			this.pile.add(cg);
			this.groupes.add(i, cg);
		}
	}

	public void genere() {
		Random rnd = new Random();
		this.creuse();
		while (pile.size() > 1) {

			List<Integer> cg = pile.remove(rnd.nextInt(pile.size()));
			boolean find = false;
			Integer c1 = null;
			Integer c2 = null;
			int dir = -1;
			while (!find) {
				c1 = cg.get(rnd.nextInt(cg.size()));
				int l = c1 % largeur;
				int h = c1 / largeur;
				List<Integer> dirs = new ArrayList<>();
				if (l > 0 && this.positions[c1] != this.positions[c1 - 1]) {
					dirs.add(MUR_OUEST);
				}
				if (l < (largeur - 1) && this.positions[c1] != this.positions[c1 + 1]) {
					dirs.add(MUR_EST);
				}
				if (h > 0 && this.positions[c1] != this.positions[c1 - largeur]) {
					dirs.add(MUR_NORD);
				}
				if (h < (hauteur - 1) && this.positions[c1] != this.positions[c1 + largeur]) {
					dirs.add(MUR_SUD);
				}
				if (dirs.size() > 0) {
					dir = dirs.get(rnd.nextInt(dirs.size()));
					find = true;
				}
			}

			switch (dir) {
			case MUR_NORD:
				c2 = c1 - largeur;
				data[c1] -= MUR_NORD;
				data[c2] -= MUR_SUD;
				break;
			case MUR_SUD:
				c2 = c1 + largeur;
				data[c1] -= MUR_SUD;
				data[c2] -= MUR_NORD;
				break;
			case MUR_EST:
				c2 = c1 + 1;
				data[c1] -= MUR_EST;
				data[c2] -= MUR_OUEST;
				break;
			case MUR_OUEST:
				c2 = c1 - 1;
				data[c1] -= MUR_OUEST;
				data[c2] -= MUR_EST;
				break;
			}

			this.groupes.get(c2).addAll(cg);

			for (Integer c : this.groupes.get(c2)) {
				this.positions[c] = c2;
				this.groupes.set(c, this.groupes.get(c2));
			}
		}

		this.makeTable();
		System.out.println(largeurTable + " " + hauteurTable);
		System.out.println(largeur + " " + hauteur);
	}

	public int getPosition(int i) {
		return this.table[i];
	}

	private void printValue(PrintStream out) {
		for (int i = 0; i < size; i++) {
			out.print(data[i]);
			if (data[i] < 10)
				out.print(" ");
			if (data[i] < 100)
				out.print(" ");
			out.print(" ");

			if ((i % largeur) == (largeur - 1))
				out.println();
		}
	}

	private void makeTable() {
		largeurTable = 1 + largeur * 2;
		hauteurTable = 1 + hauteur * 2;
		int nb = largeurTable * hauteurTable;

		this.table = new int[nb];
		for (int i = 0; i < nb; i++)
			this.table[i] = 1;

		int x = 1;
		int y = 1;
		for (int i = 0; i < size; i++) {
			int n = x + y * largeurTable;

			this.table[n] = 0;

			if (!isMur(data[i], MUR_EST)) {
				this.table[n + 1] = 0;
			}
			if (!isMur(data[i], MUR_SUD)) {
				this.table[n + largeurTable] = 0;
			}

			if ((i % largeur) == (largeur - 1)) {
				x = 1;
				y += 2;
			} else {
				x += 2;
			}
		}
	}

	public void print(PrintStream out) {

		// print
		for (int i = 0; i < (largeurTable * hauteurTable); i++) {
			if (this.table[i] == 1)
				System.out.print("O");
			else if (this.table[i] == 0)
				System.out.print(" ");

			if ((i % largeurTable) == (largeurTable - 1))
				System.out.println();
		}
	}

	public static void main(String[] args) {

		Labyrinthe l = new Labyrinthe(6, 6);
		l.genere();
		l.printValue(System.out);
		l.print(System.out);

	}

	private boolean isMur(int code, int mask) {
		if (code >= MUR_NORD) {
			if (MUR_NORD == mask)
				return true;
			code -= MUR_NORD;
		}
		if (code >= MUR_SUD) {
			if (MUR_SUD == mask)
				return true;
			code -= MUR_SUD;
		}
		if (code >= MUR_EST) {
			if (MUR_EST == mask)
				return true;
			code -= MUR_EST;
		}
		if (code >= MUR_OUEST) {
			if (MUR_OUEST == mask)
				return true;
			code -= MUR_OUEST;
		}

		return false;
	}

	public int getLargeur() {
		return largeur;
	}

	public int getHauteur() {
		return hauteur;
	}

	public int[] getTable() {
		return table;
	}

	public int getLargeurTable() {
		return largeurTable;
	}

	public int getHauteurTable() {
		return hauteurTable;
	}

	public int getSize() {
		return size;
	}

	private void creuse() {

		// for(int i=1;i<(hauteur-1);i++){
		// for(int j=1;j<(largeur-1);j+=2){
		// int n = j + i * largeur;
		// data[n] -= MUR_EST;
		// data[n+1] -= MUR_OUEST;
		// }
		// }
		//
		// this.makeTable();
		// this.printValue(System.out);
		// this.print(System.out);
	}
}
