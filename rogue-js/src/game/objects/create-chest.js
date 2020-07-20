import typeObject from "./type-object";
import { openChestTodo } from "../todo";
import { randomInt } from "../../commons";

let INDEX = 0;

const TYPES = {
  chest: {
    code: "chest",
    desc: "un coffre",
    size: 0,
    type: typeObject.chest,
  },
  chestKey: {
    code: "chest-key",
    desc: "une de cle de coffre",
    size: 1,
    type: typeObject.key,
  },
};

export const CHEST_KIND = {
  red: "Un coffre rouge",
  green: "Un coffre vert",
  blue: "Un coffre bleu",
};

const KEY_KIND = ["Une clef rouge", "Une clef verte", "Une clef bleue"];

function createChest() {
  const chestId = `chest-id-${INDEX++}`;
  const kinds = Object.values(CHEST_KIND);
  const kind = randomInt(kinds.length);
  const chest = {
    ...TYPES.chest,
    desc: kinds[kind],
    id: chestId,
    kind: kinds[kind],
    takeable: false,
  };
  chest.loot = function (chest) {
    // TODO loot
    return [];
  };
  chest.todo = [{ desc: `Ouvrir ${chest.desc}`, todo: openChestTodo }];

  const key = {
    ...TYPES.chestKey,
    id: `chest-key-id-${INDEX++}`,
    desc: KEY_KIND[kind],
    target: kinds[kind],
    takeable: true,
    todo: [],
  };

  return [chest, key];
}

export default createChest;
