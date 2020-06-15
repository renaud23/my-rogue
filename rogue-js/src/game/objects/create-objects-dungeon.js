import { randomInt } from "../../commons";
import { createDoor, createStairsUp, createStairsDown } from "./specials";
import { createRandomSimple } from "./simple";
import createChest from "./create-chest";
import { popOne, peekOne } from "../commons";

const NB_CHEST = 2;

function createChestAndKey(level, empties) {
  return new Array(NB_CHEST).fill(null).reduce(function (a) {
    const posChest = popOne(empties, level);
    const posKey = peekOne(empties, level);
    const [chest, key] = createChest();
    return [
      ...a,
      { ...chest, position: posChest, level },
      { ...key, position: posKey, level },
    ];
  }, []);
}

function createDoors(doors, level, empties) {
  return doors.map(function (position) {
    popOne(empties, level);
    return createDoor(position, level);
  });
}

function createStairs(dungeon, level) {
  return Object.entries(dungeon.getStairs(level)).map(function ([
    kind,
    { position },
  ]) {
    if (kind === "up") {
      return createStairsUp(position, level);
    }
    return createStairsDown(position, level);
  });
}

function createSimples(empties, level) {
  return new Array(5 + randomInt(10)).fill(null).map(function () {
    const position = peekOne(empties, level);
    return { ...createRandomSimple(), position, level };
  });
}

function createLevelObject(state, level, empties) {
  const { dungeon } = state;

  const doors = createDoors(dungeon.getDoors(level), level, empties);
  const chestsAnKeys = createChestAndKey(level, empties);
  const simples = createSimples(empties, level);
  const stairs = createStairs(dungeon, level);
  // const arrows = fillArrows(emptyTiles, level);
  return [...stairs, ...doors, ...chestsAnKeys, ...simples];
}

function createObjects(state, empties) {
  const { dungeon } = state;
  const dungeonHeight = dungeon.getDungeonHeight();

  const objects = new Array(dungeonHeight)
    .fill(null)
    .reduce(function (a, _, level) {
      return [...a, createLevelObject(state, level, empties)];
    }, []);

  return objects;
}

export default createObjects;
