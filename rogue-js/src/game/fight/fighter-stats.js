import { randomInt } from "../../commons";

// S.A.L.E
function createStats(s = 1, a = 1, l = 1, e = 1) {
  return {
    strength: s,
    agility: a,
    luck: l,
    endurance: e,
  };
}

export function createRandomStats(level) {
  if (level === 1) {
    return { strength: 1, agility: 1, luck: 1, endurance: 1 };
  }
  return new Array(level - 1).fill().reduce(
    function ({ strength, agility, luck, endurance }, _) {
      const dice = randomInt(8);
      if (dice < 3) {
        return { strength: strength + 1, agility, luck, endurance };
      }
      if (dice < 5) {
        return { strength, agility: agility + 1, luck, endurance };
      }
      if (dice < 6) {
        return { strength, agility, luck: luck + 1, endurance };
      }
      return { strength, agility, luck, endurance: endurance + 1 };
    },
    { strength: 1, agility: 1, luck: 1, endurance: 1 }
  );
}

export default createStats;
