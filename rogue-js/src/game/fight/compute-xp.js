const S = 5; // sum start S.A.L.E (2,1,1,1)
const THAU = 4;

function LV(i) {
  if (i <= 1) {
    return 0;
  }
  return THAU * (i - 1) * (S + i - 1);
}

function createTable(lim) {
  return new Array(lim).fill(0).map((_, i) => LV(i));
}

const TABLE_LEVEL_XP = createTable(50);

export function levelForXp(xp) {
  return TABLE_LEVEL_XP.reduce((a, x, i) => (x < xp ? i : a), 0);
}

function isDead(o) {
  const { stats } = o;
  const { life } = stats;

  return life <= 0;
}

export function enemyXpValue(enemy) {
  const { stats } = enemy;
  const { level, strength, agility, luck, endurance } = stats;
  return level * (strength + agility + luck + endurance);
}

export function ennemiesXpValue(ennemies) {
  return ennemies.reduce(function (how, e) {
    return how + enemyXpValue(e);
  }, 0);
}

export function computeMaxLife(stats) {
  const { level, endurance } = stats;
  const maxLife = 10 * (level + endurance + 1);
  return { ...stats, maxLife, life: maxLife };
}

export function computeNextLevelXp(stats) {
  const { level, strength, agility, luck, endurance } = stats;
  return {
    ...stats,
    nextLevelXp: level * (strength + agility + luck + endurance) * THAU,
  };
}

function checkLevelPlayer(player, gain) {
  const { stats } = player;
  const { level, xp, nextLevelXp, xpPoints } = stats;
  const nextXp = xp + gain;

  if (nextXp >= nextLevelXp) {
    const nextStats = {
      ...stats,
      xp: nextXp - nextLevelXp,
      level: level + 1,
      xpPoints: xpPoints + 1,
    };

    return checkLevelPlayer(
      {
        ...player,
        stats: computeMaxLife(computeNextLevelXp(nextStats)),
      },
      0
    );
  }

  return { ...player, stats: { ...stats, xp: nextXp } };
}

function computeXP(player, enemy) {
  if (isDead(enemy)) {
    const gain = enemyXpValue(enemy);
    return checkLevelPlayer(player, gain);
  }
  return player;
}

export default computeXP;
