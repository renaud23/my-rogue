import { peekOne, randomInt } from "../../commons";
import { createRandomSimple } from "./simple";

function createLevelObject(state, level) {
  const { dungeon } = state;
  const how = 3 + randomInt(3);
  return new Array(how).fill(null).map(function () {
    const position = peekOne(dungeon.getEmptyTiles(level));
    return { ...createRandomSimple(), position, level };
  });
}

function createObjects(state) {
  const { dungeon } = state;
  const dungeonHeight = dungeon.getDungeonHeight();

  const objects = new Array(dungeonHeight)
    .fill(null)
    .reduce(function (a, _, level) {
      return [...a, createLevelObject(state, level)];
    }, []);

  return objects;
}

export default createObjects;
