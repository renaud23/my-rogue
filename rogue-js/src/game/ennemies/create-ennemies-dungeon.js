import { createRat } from "./rat";
import { enemyXpValue, levelForXp } from "../fight/compute-xp";
import { peekOne, randomInt } from "../../commons";
let INDEX = 0;

function createLevelRat(state, level, levelMin) {
  const { dungeon } = state;
  const how = 2 + randomInt(4);
  return new Array(how).fill(null).map(function () {
    const position = peekOne(dungeon.getEmptyTiles(level));
    return {
      id: `rat-${INDEX++}`,
      position,
      level,
      ...createRat(randomInt(2) + Math.max(1, levelMin)),
    };
  });
}

export function createRatsDungeon(state) {
  const { dungeon } = state;
  const dungeonHeight = dungeon.getDungeonHeight();

  const [ennemies] = new Array(dungeonHeight).fill(null).reduce(
    function ([a, sumXp], _, i) {
      const ennemiesLevel = createLevelRat(state, i, levelForXp(sumXp));
      const levelXp = ennemiesLevel.reduce(
        (tot, e) => tot + enemyXpValue(e),
        0
      );
      return [[...a, ennemiesLevel], sumXp + levelXp];
    },
    [[], 0]
  );
  return ennemies;
}

function create(state) {
  return [...createRatsDungeon(state)];
}

export default create;
