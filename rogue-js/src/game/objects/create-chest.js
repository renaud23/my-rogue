import typeObject from "./type-object";
import { createOpenChestTodo } from "../todo";
import { randomInt } from "../../commons";

let INDEX = 0;

const TYPES = {
  chest: {
    code: 2000,
    desc: "un coffre",
    size: 0,
    type: typeObject.chest,
  },
  chestKey: {
    code: 2001,
    desc: "une de cle de coffre",
    size: 1,
    type: typeObject.key,
  },
};

const CHEST_KIND = [
  "en bois",
  "en or",
  "en verre",
  "en os",
  "en argent",
  "en fer",
  "sombre",
  "rouge",
  "en ébène",
];

function createChest() {
  const chestId = `chest-id-${INDEX++}`;
  const kind = CHEST_KIND[randomInt(CHEST_KIND.length)];
  const chest = {
    ...TYPES.chest,
    desc: `un coffre ${kind}`,
    id: chestId,
    kind,
    takeable: false,
  };
  chest.loot = function (chest) {
    // TODO loot
    return [];
  };
  chest.todo = [
    { desc: `Ouvrir ${chest.desc}`, todo: createOpenChestTodo(chest) },
  ];

  const key = {
    ...TYPES.chestKey,
    id: `chest-key-id-${INDEX++}`,
    desc: `une clef ${kind}`,
    target: kind,
    takeable: true,
    todo: [],
  };

  return [chest, key];
}

export default createChest;
