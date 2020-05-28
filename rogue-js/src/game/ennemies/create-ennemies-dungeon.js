import { createRat } from "./rat";
import { peekOne, randomInt } from "../../commons";
let INDEX = 0;

function createLevelRat(state, level) {
  const { dungeon } = state;
  const how = 2 + randomInt(3);
  return new Array(how).fill(null).map(function () {
    const position = peekOne(dungeon.getEmptyTiles(level));
    return {
      id: `rat-${INDEX++}`,
      position,
      level,
      ...createRat(randomInt(2) + 1),
    };
  });
}

export function createRatsDungeon(state) {
  const { dungeon } = state;
  const dungeonHeight = dungeon.getDungeonHeight();

  const rats = new Array(dungeonHeight)
    .fill(null)
    .reduce(function (a, _, level) {
      return [...a, createLevelRat(state, level)];
    }, []);
  return rats;
}

function create(state) {
  return [...createRatsDungeon(state)];
}

export default create;
