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
  "chêne",
  "acajou",
  "cuivre",
  "étain",
  "bronze",
  "fer",
  "argent",
  "or",
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
    doorId: INDEX_DOOR,
    kind,
    opened: false,
    todo: [{ desc: doorDesc, todo: openDoorTodo }],
  };
}

export function createKey(
  position,
  level,
  doors = [{ id: "-1", kind: "pass" }]
) {
  const targets = doors.map(({ doorId }) => doorId);
  const kind = doors[0].kind;
  return {
    ...TYPES.keyDoor,
    desc: ({ kind }) => `Une clef en ${kind}`,
    id: `door-key-${level}-${INDEX_DOOR++}`,
    position,
    level,
    kind,
    targets,
    todo: [],
  };
}
