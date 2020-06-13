const S = 5; // sum of starting S.A.L.E (2,1,1,1)
const THETA = 3;

/*
 * XP formula : level * (strength + agility + luck + endurance) * THETA
 */

function LV(i) {
  if (i <= 1) {
    return 0;
  }
  return THETA * (i - 1) * (S + i - 1);
}

function createTable(lim) {
  return new Array(lim).fill(0).map((_, i) => LV(i));
}

const TABLE_LEVEL_XP = createTable(70);

export function computeNextLevelXp(stats) {
  const { level, strength, agility, luck, endurance } = stats;
  return {
    ...stats,
    nextLevelXp: level * (strength + agility + luck + endurance) * THETA,
  };
}

export function levelForXp(xp) {
  return TABLE_LEVEL_XP.reduce((a, x, i) => (x < xp ? i : a), 0);
}

export function enemyXpValue(enemy) {
  const { stats } = enemy;
  const { level, strength, agility, luck, endurance } = stats;
  return Math.trunc(
    Math.log(level * (strength + agility + luck + endurance)) * 2
  );
}

export default TABLE_LEVEL_XP;
