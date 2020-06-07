import { peekOne, randomInt, popOne } from "../../commons";
import { createRandomSimple } from "./simple";
import createChest from "./create-chest";
// import { createArrows } from "./ammo";

const NB_CHEST = 2;

// function fillArrows(emptyTiles, level) {
//   return new Array(5).fill(null).map(function (a) {
//     return { ...createArrows(5), level, position: peekOne(emptyTiles) };
//   });
// }

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
  const simples = new Array(5 + randomInt(10)).fill(null).map(function () {
    const position = peekOne(emptyTiles);
    return { ...createRandomSimple(), position, level };
  });
  // const arrows = fillArrows(emptyTiles, level);
  return [...chestsAnKeys, ...simples];
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
