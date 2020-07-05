import { WALL_CODES } from "../../game/commons/dungeon-tiles";
import { TYPE_OBJECT, CHEST_KIND } from "../../game/objects";
import { TYPE_ENNEMIES } from "../../game/ennemies/commons/type-ennemies";

const ON_CENTER = 0b000010000;
const ON_SOUTH = 0b000000010;

export function getWallsText(code) {
  const base = { width: 32, height: 32 };
  if (code === 0) {
    return undefined;
  }
  // switch (code) {
  //   // case WALL_CODES.NORTH_OR_SOUTH:
  //   //   return { ...base, x: 34, y: 129 };
  //   case WALL_CODES.WEST:
  //     return { ...base, x: 1, y: 129 };
  //   case WALL_CODES.EAST:
  //     return { ...base, x: 67, y: 129 }; // 1 261
  //   case WALL_CODES.CORNER_WEST:
  //     return { ...base, x: 1, y: 162 };
  //   case WALL_CODES.CORNER_EAST:
  //     return { ...base, x: 67, y: 162 };
  //   case WALL_CODES.PILAR_WEST:
  //     return { ...base, x: 1, y: 195 };
  //   case WALL_CODES.PILAR_EAST:
  //     return { ...base, x: 67, y: 195 };
  //   default:
  //     return { ...base, x: 34, y: 129 };
  // }
  if ((code & ON_SOUTH) === ON_SOUTH && (code & ON_CENTER) === ON_CENTER) {
    return { ...base, x: 1, y: 261 };
  }

  return { ...base, x: 34, y: 129 };
}

export function getGroundTex() {
  return { x: 67, y: 228, width: 32, height: 32 };
}

export function getPlayerTex() {
  return { x: 49, y: 1, width: 32, height: 32 };
}

export function getEnemyTex(enemy) {
  const { type } = enemy;
  switch (type) {
    case TYPE_ENNEMIES.rat:
      return { x: 113, y: 1, width: 32, height: 32 };
    default:
      return { x: 82, y: 1, width: 32, height: 32 };
  }
}

export function getIronSightTex() {
  return { x: 49, y: 34, width: 16, height: 16 };
}

/** */

export function getPotionTex() {
  return { x: 113, y: 131, width: 32, height: 32 };
}

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
    return { x: 34, y: 162, width: 32, height: 32 };
  }
  return { x: 34, y: 195, width: 32, height: 32 };
}

export function getStairsTex(stairs) {
  const { type } = stairs;
  if (type === TYPE_OBJECT.stairsUp) {
    return { x: 1, y: 228, width: 32, height: 32 };
  }
  return { x: 34, y: 228, width: 32, height: 32 };
}

export function getSimpleTex(simple) {
  const { code } = simple;
  switch (code) {
    default:
      return { x: 212, y: 65, width: 32, height: 32 };
  }
}

export function getCorpseTex() {
  return { x: 212, y: 98, width: 32, height: 32 };
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
    case TYPE_OBJECT.stairsDown:
    case TYPE_OBJECT.stairsUp:
      return getStairsTex(object);
    case TYPE_OBJECT.simple:
      return getSimpleTex(object);
    case TYPE_OBJECT.corpse:
      return getCorpseTex();
    case TYPE_OBJECT.potion:
      return getPotionTex();
    default:
      return { x: 66, y: 34, width: 16, height: 16 };
  }
}
