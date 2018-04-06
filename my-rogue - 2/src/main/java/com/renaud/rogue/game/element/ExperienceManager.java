package com.renaud.rogue.game.element;

public class ExperienceManager implements XpEarning {

	private Joueur joueur;
	private int level;
	private int xp;
	private int xpForNextLevel;
	private int xpTotal;

	public ExperienceManager() {
		this.level = 1;
		this.xp = 0;
		this.xpTotal = 0;
		this.xpForNextLevel = computeXpForLevel(this.level);
	}

	public void killMonster(Monster monster) {
		xp += monster.getXp();
		this.xpTotal += monster.getXp();
		if (this.xp >= this.xpForNextLevel) {
			this.xp -= this.xpForNextLevel;
			this.xpForNextLevel = computeXpForLevel(++this.level);
			System.out.println("Vous franchissez un level : " + this.level);
		}
	}

	public int getLevel() {
		return level;
	}

	public int getXp() {
		return xp;
	}

	public int getXpTotal() {
		return xpTotal;
	}

	public int getXpForNextLevel() {
		return xpForNextLevel;
	}

	public static int computeXpForLevel(int level) {
		return (int) (Math.pow(level, 1.5) * 20);
	}

}
