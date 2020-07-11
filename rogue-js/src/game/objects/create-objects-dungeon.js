import { randomInt } from "../../commons";
import { createDoor, createStairsUp, createStairsDown } from "./specials";
import { createRandomSimple } from "./simple";
import createChest from "./create-chest";
import { popOne, peekOne } from "../commons";
import createDungeonObjects from "./dungeon-objects";
import { createArrows } from "./ammo";
import createLevelDoors from "../dungeon/create-level-doors";

const NB_CHEST = 2;

function fillArrows(state, level, empties) {
  return new Array(2).fill(null).map(function () {
    const position = empties.peekOne(level);
    return { ...createArrows(5), level, position };
  });
}

function createChestAndKey(state, level, empties) {
  return new Array(NB_CHEST).fill(null).reduce(function (a) {
    const posChest = empties.popOne(level);
    const posKey = empties.peekOne(level);
    const [chest, key] = createChest();
    return [
      ...a,
      { ...chest, position: posChest, level },
      { ...key, position: posKey, level },
    ];
  }, []);
}

function createStairs(state, level, empties) {
  const { dungeon } = state;
  return Object.entries(dungeon.getStairs(level)).map(function ([
    kind,
    { position },
  ]) {
    empties.removePosition(level, position);
    if (kind === "up") {
      return createStairsUp(position, level);
    }
    return createStairsDown(position, level);
  });
}

function fillWithSimples(state, level, empties) {
  return new Array(5 + randomInt(10)).fill(null).map(function () {
    const position = empties.peekOne(level);
    return { ...createRandomSimple(), position, level };
  });
}

function fillWithDoors(state, level) {
  const { dungeon } = state;

  return createLevelDoors(dungeon.getLevel(level), level);
}

function fillDungeonLevels(state, empties) {
  const { dungeon } = state;
  const dungeonHeight = dungeon.getDungeonHeight();
  const doors = new Array(dungeonHeight)
    .fill(null)
    .reduce(function (precedents, _, level) {
      return [
        ...precedents,
        ...createStairs(state, level, empties),
        ...fillWithDoors(state, level),
        ...createChestAndKey(state, level, empties),
        ...fillWithSimples(state, level, empties),
        ...fillArrows(state, level, empties),
      ];
    }, []);

  return doors;
}

function createObjects(state, empties) {
  return createDungeonObjects(fillDungeonLevels(state, empties));
}

export default createObjects;
