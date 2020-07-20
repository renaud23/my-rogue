import { enemyXpValue } from "../fight";
import createEnemy from "./create-enemy";
import { popOne } from "../commons";

let INDEX = 0;
const DENSITY = 1 / 50;

function fillLevel(state, level, sumXp, empties) {
  const nb = Math.round(empties.count(level) * DENSITY);

  return new Array(nb).fill(undefined).reduce(
    function ([ennemies, currXp]) {
      const position = empties.popOne(level);
      const enemy = {
        ...createEnemy(currXp),
        id: `enemy-${INDEX++}`,
        position,
        level,
      };
      return [[...ennemies, enemy], currXp + enemyXpValue(enemy)];
    },
    [[], sumXp]
  );
}

function createEnnemies(state, empties) {
  const { dungeon } = state;
  const dungeonHeight = dungeon.getDungeonHeight();
  const [ennemies] = new Array(dungeonHeight).fill(undefined).reduce(
    function ([levels, sumXp], _, i) {
      const [level, levelXp] = fillLevel(state, i, sumXp, empties);
      return [[...levels, level], sumXp + levelXp];
    },
    [[], 0]
  );
  return ennemies;
}

export default createEnnemies;
