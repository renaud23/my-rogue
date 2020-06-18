import typeObject from "./type-object";
import { splitDoor } from "../todo";
import { stairsUpTodo, stairsDownTodo } from "../todo";

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

  stairsUp: {
    code: 2002,
    desc: "des escaliers montants",
    takeable: false,
    aggregative: false,
    opened: false,
    locked: false,
    type: typeObject.stairsUp,
  },
  stairsDown: {
    code: 2003,
    desc: "des escaliers descendants",
    takeable: false,
    aggregative: false,
    opened: false,
    locked: false,
    type: typeObject.stairsDown,
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
        todo: splitDoor,
      },
    ],
  };
}

export function createStairsUp(position, level) {
  return {
    ...SPECIAL_MAP.stairsUp,
    id: `stairs-up-${INDEX++}`,
    position,
    level,
    todo: [{ desc: "Monter l'escalier", todo: stairsUpTodo }],
  };
}

export function createStairsDown(position, level) {
  return {
    ...SPECIAL_MAP.stairsDown,
    id: `stairs-down-${INDEX++}`,
    position,
    level,
    todo: [{ desc: "Descendre l'escalier", todo: stairsDownTodo }],
  };
}
