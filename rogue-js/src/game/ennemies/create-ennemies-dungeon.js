import { enemyXpValue } from "../fight";
import createEnemy from "./create-enemy";
import { randomInt } from "../../commons";
import { popOne } from "../commons";

let INDEX = 0;

function fillLevel(state, level, sumXp, empties) {
  const nb = 3 + randomInt(5);
  const { dungeon } = state;

  return new Array(nb).fill(undefined).reduce(
    function ([ennemies, currXp]) {
      const position = popOne(empties, level);
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
