import { openDoorTodo } from "../todo";
import typeObject from "./type-object";

let INDEX_DOOR = 1;

const DOOR_KINDS_LIST = [
  { desc: "bois", id: "wood" },
  { desc: "ébène", id: "ebony" },
  { desc: "bronze", id: "bronze" },
  { desc: "fer", id: "iron" },
  { desc: "argent", id: "silver" },
  { desc: "or", id: "gold" },
];
export const DOOR_KIND = DOOR_KINDS_LIST.reduce(function (a, k) {
  const { id } = k;
  return { ...a, [id]: id };
}, {});

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

export function generateDoorKind(index = 0) {
  return DOOR_KINDS_LIST[Math.min(index, DOOR_KINDS_LIST.length - 1)];
}

export function createDoor(
  position,
  level,
  kind = DOOR_KINDS_LIST[0],
  locked = false
) {
  return {
    ...TYPES.door,
    desc: ({ kind }) => `une porte en ${kind.desc}`,
    id: `door-${level}-${INDEX_DOOR++}`,
    takeable: false,
    level,
    position,
    locked,
    lockId: `door-${level}-${INDEX_DOOR++}`,
    kind,
    opened: false,
    todo: [{ desc: doorDesc, todo: openDoorTodo }],
  };
}

export function canOpen(key, door) {
  const { targets = [] } = key;
  const { lockId } = door;
  return targets.indexOf(lockId) !== -1;
}

export function unlockedAndOpenDoor(door) {
  return { ...door, opened: true, locked: false };
}

export function switchDoor(door) {
  const { opened } = door;
  return { ...door, opened: !opened };
}
