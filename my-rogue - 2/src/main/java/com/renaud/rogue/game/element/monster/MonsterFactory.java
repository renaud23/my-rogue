package com.renaud.rogue.game.element.monster;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.renaud.rogue.game.element.ExperienceManager;
import com.renaud.rogue.game.element.Monster;

public class MonsterFactory {

	private final static Random rnd = new Random();
	private final static List<Class<? extends Monster>> levelOne = new ArrayList<>();

	static {
		levelOne.add(Bat.class);
		levelOne.add(Ghoul.class);

	}

	public static List<Monster> createMonsters(int level) {
		int xpEscompted = ExperienceManager.computeXpForLevel(level);
		List<Monster> monsters = new ArrayList<>();
		int xpCurr = 0;
		while (xpCurr < xpEscompted) {
			int alea = rnd.nextInt(100) > 90 ? 1 : 0;
			Monster m = createMonster(level + alea);
			xpCurr += m.getXp();
			monsters.add(m);
		}

		return monsters;
	}

	public static Monster createMonster(int level) {
		switch (level) {
			case 1:
				return getLevelOne(level);
			default:
				return getLevelOne(1);
		}
	}

	static private Monster getLevelOne(int level) {
		Class<? extends Monster> monsterClass = levelOne.get(rnd.nextInt(levelOne.size()));
		try {
			return monsterClass.getConstructor(Integer.TYPE).newInstance(level);
		}
		catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}
}
