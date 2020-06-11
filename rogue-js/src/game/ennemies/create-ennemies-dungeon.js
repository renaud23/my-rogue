import { createRat } from "./rat";
import createWolf from "./wolf";
import { enemyXpValue, levelForXp } from "../fight/compute-xp";
import { peekOne, randomInt } from "../../commons";
let INDEX = 0;

function createLevelEnnemies(state, level, how, levelMin, create) {
  const { dungeon } = state;
  return new Array(how).fill(null).map(function () {
    const position = peekOne(dungeon.getEmptyTiles(level));
    return {
      id: `enemy-${INDEX++}`,
      position,
      level,
      ...create(randomInt(2) + Math.max(1, levelMin)),
    };
  });
}

export function createEnnemiesDungeon(state, how, create, xp = 0) {
  const { dungeon } = state;
  const dungeonHeight = dungeon.getDungeonHeight();
  const [ennemies] = new Array(dungeonHeight).fill(null).reduce(
    function ([a, sumXp], _, i) {
      const ennemiesLevel = createLevelEnnemies(
        state,
        i,
        how,
        levelForXp(sumXp),
        create
      ); //(state, level, how, levelMin, create)
      const levelXp = ennemiesLevel.reduce(
        (tot, e) => tot + enemyXpValue(e),
        0
      );
      return [[...a, ennemiesLevel], sumXp + levelXp];
    },
    [[], xp]
  );
  return ennemies;
}

function create(state) {
  const rats = createEnnemiesDungeon(state, 6 + randomInt(2), createRat);
  const wolfs = createEnnemiesDungeon(state, 3 + randomInt(2), createWolf);
  const { dungeon } = state;
  const dungeonHeight = dungeon.getDungeonHeight();

  return new Array(dungeonHeight).fill(undefined).map(function (_, i) {
    return [...rats[i], ...wolfs[i]];
  });
}

export default create;
