import typeObject from "./type-object";
import createSplitDoor from "../todo/create-split-door";

let INDEX = 0;

export const SPECIAL_MAP = {
  corpse: {
    code: 2000,
    originalDesc: "un cadavre",
    desc: "un cadavre",
    takeable: false,
    aggregative: false,
    type: typeObject.corpse,
  },
  door: {
    code: 2001,
    opened: false,
    locked: false,
    originalDesc: "une porte",
    desc: "une porte",
    takeable: false,
    aggregative: false,
    type: typeObject.door,
  },
};

export function createCorpse(enemy) {
  const { position, level } = enemy;
  return { ...SPECIAL_MAP.corpse, position, level, todo: [] };
}

export function createDoor(position, level, opened = false) {
  const door = {
    ...SPECIAL_MAP.door,
    position,
    opened,
    desc: `Une porte ${opened ? "ouverte" : "fermée"}`,
    level,
    id: `door-${INDEX++}`,
  };

  return {
    ...door,
    todo: [
      {
        desc: `${opened ? "Fermer" : "Ouvrir"} la porte`,
        todo: createSplitDoor(door),
      },
    ],
  };
}
