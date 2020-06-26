import { WALL_CODES } from "../../game/commons/dungeon-tiles";
import { TYPE_OBJECT, CHEST_KIND } from "../../game/objects";

export function getWallsText(code) {
  const base = { width: 16, height: 16 };
  switch (code) {
    case WALL_CODES.NORTH_OR_SOUTH:
      return { ...base, x: 18, y: 129 };
    case WALL_CODES.WEST:
      return { ...base, x: 1, y: 129 };
    case WALL_CODES.EAST:
      return { ...base, x: 35, y: 129 };
    case WALL_CODES.CORNER_WEST:
      return { ...base, x: 1, y: 146 };
    case WALL_CODES.CORNER_EAST:
      return { ...base, x: 35, y: 146 };
    case WALL_CODES.PILAR_WEST:
      return { ...base, x: 1, y: 163 };
    case WALL_CODES.PILAR_EAST:
      return { ...base, x: 35, y: 163 };
    default:
      return undefined;
  }
}

export function getGroundTex() {
  return { x: 0, y: 64, width: 16, height: 16 };
}

export function getPlayerTex() {
  return { x: 49, y: 1, width: 32, height: 32 };
}

export function getEnemyTex() {
  return { x: 82, y: 1, width: 32, height: 32 };
}

export function getIronSightTex() {
  return { x: 49, y: 34, width: 16, height: 16 };
}

/** */

function getChest(chest) {
  const { kind } = chest;
  switch (kind) {
    case CHEST_KIND.red:
      return { x: 113, y: 65, width: 32, height: 32 };
    case CHEST_KIND.green:
      return { x: 146, y: 65, width: 32, height: 32 };
    case CHEST_KIND.blue:
      return { x: 179, y: 65, width: 32, height: 32 };
    default:
      return { x: 113, y: 65, width: 32, height: 32 };
  }
}

function getKey(key) {
  const { target } = key;
  switch (target) {
    case CHEST_KIND.red:
      return { x: 113, y: 98, width: 32, height: 32 };
    case CHEST_KIND.green:
      return { x: 146, y: 98, width: 32, height: 32 };
    case CHEST_KIND.blue:
      return { x: 179, y: 98, width: 32, height: 32 };
    default:
      return { x: 113, y: 65, width: 32, height: 32 };
  }
}

function getDoorTex(door) {
  const { opened } = door;
  if (opened) {
    return { x: 18, y: 146, width: 16, height: 16 };
  }
  return { x: 18, y: 163, width: 16, height: 16 };
}

export function getObjectTex(object) {
  const { type } = object;
  switch (type) {
    case TYPE_OBJECT.chest:
      return getChest(object);
    case TYPE_OBJECT.key:
      return getKey(object);
    case TYPE_OBJECT.door:
      return getDoorTex(object);
    default:
      return { x: 66, y: 34, width: 16, height: 16 };
  }
}
