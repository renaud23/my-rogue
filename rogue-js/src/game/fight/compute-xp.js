import { fillMessage } from "../commons";
import { computeNextLevelXp, enemyXpValue } from "./table-xp";
import PATTERNS from "../message-patterns";

function isDead(o) {
  const { stats } = o;
  const { life } = stats;

  return life <= 0;
}

export function ennemiesXpValue(ennemies) {
  return ennemies.reduce(function (how, e) {
    return how + enemyXpValue(e);
  }, 0);
}

export function computeMaxLife(stats, base = 10) {
  const { level, endurance } = stats;
  const maxLife = base * (level + endurance);
  return { ...stats, maxLife, life: maxLife };
}

function checkLevelPlayer(player, gain, messages = []) {
  const { stats } = player;
  const { level, xp, nextLevelXp, xpPoint } = stats;
  const nextXp = xp + gain;

  if (nextXp >= nextLevelXp) {
    const nextStats = {
      ...stats,
      xp: nextXp - nextLevelXp,
      level: level + 1,
      xpPoint: xpPoint + 1,
    };
    return checkLevelPlayer(
      {
        ...player,
        stats: computeMaxLife(computeNextLevelXp(nextStats)),
      },
      0,
      [...messages, fillMessage(PATTERNS.xpProgress, { stats: nextStats })]
    );
  }

  return [{ ...player, stats: { ...stats, xp: nextXp } }, messages];
}

function computeXP(player, enemy) {
  if (isDead(enemy)) {
    const gain = enemyXpValue(enemy);
    return checkLevelPlayer(player, gain);
  }
  return [player, []];
}

export default computeXP;
