import { openDoorTodo } from "../todo";
import typeObject from "./type-object";

let INDEX_DOOR = 1;

const TYPES = {
  door: {
    code: "door",
    desc: "une porte",
    size: 0,
    type: typeObject.door,
  },
  keyDoor: {
    code: "door-key",
    desc: "une de cle de porte",
    size: 1,
    takeable: true,
    type: typeObject.key,
  },
};

function doorDesc(door) {
  const { opened, desc } = door;
  if (opened) {
    return `Fermer ${desc(door)}`;
  }
  return `Ouvrir ${desc(door)}`;
}

export const DOOR_KINDS = [
  "bois pourri",
  "bois",
  "bois sculpté",
  "cuivre",
  "étain",
  "bronze",
  "fer rouillé",
  "fer",
  "argent",
  "argent massif",
  "or",
  "or massif",
];

export function generateDoorKind(index = 0) {
  return DOOR_KINDS[Math.min(index, DOOR_KINDS.length - 1)];
}

export function createDoor(
  position,
  level,
  kind = DOOR_KINDS[0],
  locked = false
) {
  return {
    ...TYPES.door,
    desc: ({ kind }) => `une porte en ${kind}`,
    id: `door-${level}-${INDEX_DOOR++}`,
    takeable: false,
    level,
    position,
    locked,
    kind,
    opened: false,
    todo: [{ desc: doorDesc, todo: openDoorTodo }],
  };
}

export function createKey(position, level, target) {
  return {
    ...TYPES.keyDoor,
    desc: ({ target }) => `Une clef en ${target}`,
    id: `door-key-${level}-${INDEX_DOOR++}`,
    position,
    level,
    target,
    todo: [],
  };
}

export function linkDoorsKey(key, ...doors) {
  return [key, ...doors];
}

// export function createDoor(position, level, opened = false) {
//   const door = {
//     ...SPECIAL_MAP.door,
//     position,
//     opened,
//     desc: `Une porte ${opened ? "ouverte" : "fermée"}`,
//     level,
//     id: `door-${INDEX++}`,
//   };

//   return {
//     ...door,
//     todo: [
//       {
//         desc: `${opened ? "Fermer" : "Ouvrir"} la porte`,
//         todo: splitDoor,
//       },
//     ],
//   };
// }
