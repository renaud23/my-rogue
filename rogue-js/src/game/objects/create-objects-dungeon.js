import { peekOne, randomInt, popOne, popThisOne } from "../../commons";
import { createRandomSimple } from "./simple";
import { createDoor } from "./specials";
import createChest from "./create-chest";
// import { createArrows } from "./ammo";

const NB_CHEST = 2;

// function fillArrows(emptyTiles, level) {
//   return new Array(5).fill(null).map(function (a) {
//     return { ...createArrows(5), level, position: peekOne(emptyTiles) };
//   });
// }

function createChestAndKey(emptyTiles, level) {
  return new Array(NB_CHEST).fill(null).reduce(function (a) {
    const posChest = popOne(emptyTiles);
    const posKey = peekOne(emptyTiles);
    const [chest, key] = createChest();
    return [
      ...a,
      { ...chest, position: posChest, level },
      { ...key, position: posKey, level },
    ];
  }, []);
}

function createDoors(emptyTiles, doors, level) {
  return doors.map(function (position) {
    popThisOne(emptyTiles, position);
    return createDoor(position, level);
  });
}

function createLevelObject(state, level) {
  const { dungeon } = state;
  const emptyTiles = dungeon.getEmptyTiles(level);
  const doors = createDoors(emptyTiles, dungeon.getDoors(level), level);
  const chestsAnKeys = createChestAndKey(emptyTiles, level);
  const simples = new Array(5 + randomInt(10)).fill(null).map(function () {
    const position = peekOne(emptyTiles);
    return { ...createRandomSimple(), position, level };
  });
  // const arrows = fillArrows(emptyTiles, level);

  return [...doors, ...chestsAnKeys, ...simples];
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
