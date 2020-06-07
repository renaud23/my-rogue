import { randomInt } from "../../commons";

// S.A.L.E
function createStats(s = 1, a = 1, l = 1, e = 1) {
  return {
    strength: s,
    agility: a,
    luck: l,
    endurance: e,
    level: 1,
  };
}

export function createRandomStats(level) {
  if (level === 1) {
    return { strength: 1, agility: 1, luck: 1, endurance: 1, level };
  }
  return new Array(level - 1).fill().reduce(
    function ({ strength, agility, luck, endurance, level }, _) {
      const dice = randomInt(9);
      if (dice < 3) {
        return { strength: strength + 1, agility, luck, endurance, level };
      }
      if (dice < 6) {
        return { strength, agility: agility + 1, luck, endurance, level };
      }
      if (dice < 7) {
        return { strength, agility, luck: luck + 1, endurance, level };
      }
      return { strength, agility, luck, endurance: endurance + 1, level };
    },
    { strength: 1, agility: 1, luck: 1, endurance: 1 }
  );
}

export default createStats;
