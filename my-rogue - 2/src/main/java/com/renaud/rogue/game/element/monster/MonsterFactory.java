package com.renaud.rogue.game.element.monster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.renaud.rogue.game.element.Monster;

public class MonsterFactory {

	private final static Random rnd = new Random();

	public static List<Monster> createMonsters(int xpEscompted, int level) {
		List<Monster> monsters = new ArrayList<>();
		int xpCurr = 0;
		while (xpCurr < xpEscompted) {
			Monster m = createMonster(level + rnd.nextInt(2));
			xpCurr += m.getXp();
			monsters.add(m);
		}

		return monsters;
	}

	public static Monster createMonster(int level) {
		return null;
	}
}
