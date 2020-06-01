import { peekOne, randomInt, popOne } from "../../commons";
import { createRandomSimple } from "./simple";
import createChest from "./create-chest";

const NB_CHEST = 2;

function createLevelObject(state, level) {
  const { dungeon } = state;
  const emptyTiles = dungeon.getEmptyTiles(level);

  const chestsAnKeys = new Array(NB_CHEST).fill(null).reduce(function (a) {
    const posChest = popOne(emptyTiles);
    const posKey = peekOne(emptyTiles);
    const [chest, key] = createChest();
    return [
      ...a,
      { ...chest, position: posChest, level },
      { ...key, position: posKey, level },
    ];
  }, []);

  const simples = new Array(1 + randomInt(2)).fill(null).map(function () {
    const position = peekOne(emptyTiles);
    return { ...createRandomSimple(), position, level };
  });

  return [...simples, ...chestsAnKeys];
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
