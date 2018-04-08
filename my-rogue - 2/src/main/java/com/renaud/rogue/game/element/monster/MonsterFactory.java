package com.renaud.rogue.game.element.monster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.renaud.rogue.game.element.ExperienceManager;
import com.renaud.rogue.game.element.Monster;

public class MonsterFactory {

	private final static Random rnd = new Random();

	public static List<Monster> createMonsters(int level) {
		int xpEscompted = ExperienceManager.computeXpForLevel(level);
		List<Monster> monsters = new ArrayList<>();
		int xpCurr = 0;
		while (xpCurr < xpEscompted) {
			int alea = rnd.nextInt(100) > 90 ? 1 : 0;
			Monster m = createMonster(level);
			xpCurr += m.getXp();
			monsters.add(m);
		}

		return monsters;
	}

	public static Monster createMonster(int level) {
		Monster monster = null;
		monster = new Bat(level);
		return monster;
	}
}
