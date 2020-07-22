import typeObject from "./type-object";
import { openChestTodo } from "../todo";
import { randomInt } from "../../commons";
import createKey from "./create-key";
import { computeDesc } from "../commons";

let INDEX = 0;

const TYPES = {
  chest: {
    code: "chest",
    desc: "un coffre",
    size: 0,
    type: typeObject.chest,
  },
};

const CHEST_KIND_LIST = [
  { desc: "rouge", id: "red" },
  { desc: "vert", id: "green" },
  { desc: "bleue", id: "blue" },
];
export const CHEST_KIND = CHEST_KIND_LIST.reduce(function (a, k) {
  const { id } = k;
  return { ...a, [id]: k };
}, {});

const KEY_KIND_LIST = [
  { desc: "rouge", id: "red" },
  { desc: "verte", id: "green" },
  { desc: "bleue", id: "blue" },
];
export const KEY_KIND = KEY_KIND_LIST.reduce(function (a, k) {
  const { id } = k;
  return { ...a, [id]: k };
}, {});

function createChest(level, posChest, posKey) {
  const chestId = `chest-id-${INDEX++}`;
  const kind = KEY_KIND_LIST[randomInt(KEY_KIND_LIST.length)];
  const chest = {
    ...TYPES.chest,
    desc: ({ kind }) => `une porte ${kind.desc}`,
    id: chestId,
    lockId: chestId,
    kind,
    takeable: false,
    position: posChest,
  };
  chest.loot = function (chest) {
    // TODO loot
    return [];
  };
  chest.todo = [{ desc: `Ouvrir ${computeDesc(chest)}`, todo: openChestTodo }];
  const key = createKey(
    level,
    posKey,
    [chest],
    ({ kind }) => `une clef de coffre ${kind.desc}`
  );

  return [chest, key];
}

export default createChest;
