import { enemyXpValue, levelForXp } from "../fight";
import createEnemy from "./create-enemy";
import { randomInt } from "../../commons";

let INDEX = 0;

function fillLevel(state, level, sumXp) {
  const nb = 13 + randomInt(5);
  const { dungeon } = state;

  return new Array(nb).fill(undefined).reduce(
    function ([ennemies, currXp]) {
      const position = dungeon.peekEmptyTile(level);
      const enemy = {
        ...createEnemy(currXp),
        id: `enemy-${INDEX++}`,
        position,
        level,
      };
      // console.log(level, enemy.desc, enemy.position, enemy.level);
      return [[...ennemies, enemy], currXp + enemyXpValue(enemy)];
    },
    [[], sumXp]
  );
}

function createEnnemies(state) {
  const { dungeon } = state;
  const dungeonHeight = dungeon.getDungeonHeight();
  const [ennemies] = new Array(dungeonHeight).fill(undefined).reduce(
    function ([levels, sumXp], _, i) {
      const [level, levelXp] = fillLevel(state, i, sumXp);
      return [[...levels, level], sumXp + levelXp];
    },
    [[], 0]
  );
  return ennemies;
}

export default createEnnemies;
