import { BIOMES } from "../../game";
import {
  TYPE_OBJECT,
  CHEST_KIND,
  DOOR_KIND,
  WEAPON_LIST_CODE,
} from "../../game/objects";
import { TYPE_ENNEMIES } from "../../game/ennemies/commons/type-ennemies";

const ON_CENTER = 0b000010000;
const ON_SOUTH = 0b000000010;

export function getWallsText(code, biome) {
  const base = { width: 32, height: 32 };
  if (code === 0) {
    return undefined;
  }
  if ((code & ON_SOUTH) === ON_SOUTH && (code & ON_CENTER) === ON_CENTER) {
    return { ...base, x: 34, y: 195 };
  }

  return { ...base, x: 34, y: 129 };
}

export function getGroundTex(biome) {
  if (biome === BIOMES.corridor) {
    return { x: 100, y: 228, width: 32, height: 32 };
  }
  return { x: 67, y: 228, width: 32, height: 32 };
}

export function getPlayerTex() {
  return { x: 1, y: 327, width: 32, height: 32 };
}

export function getEnemyTex(enemy) {
  const { type } = enemy;
  switch (type) {
    case TYPE_ENNEMIES.rat:
      return { x: 67, y: 327, width: 32, height: 32 };
    default:
      return { x: 34, y: 327, width: 32, height: 32 };
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
  const { id } = kind;
  switch (id) {
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
  const { kind } = key;
  const { id } = kind;
  switch (id) {
    case CHEST_KIND.red:
      return { x: 113, y: 98, width: 32, height: 32 };
    case CHEST_KIND.green:
      return { x: 146, y: 98, width: 32, height: 32 };
    case CHEST_KIND.blue:
      return { x: 179, y: 98, width: 32, height: 32 };
    case DOOR_KIND.wood:
      return { x: 1, y: 294, width: 32, height: 32 };
    case DOOR_KIND.ebony:
      return { x: 34, y: 294, width: 32, height: 32 };
    case DOOR_KIND.bronze:
      return { x: 67, y: 294, width: 32, height: 32 };
    case DOOR_KIND.iron:
      return { x: 100, y: 294, width: 32, height: 32 };
    case DOOR_KIND.silver:
      return { x: 133, y: 294, width: 32, height: 32 };
    case DOOR_KIND.gold:
      return { x: 166, y: 294, width: 32, height: 32 };
    default:
      return { x: 113, y: 98, width: 32, height: 32 };
  }
}

function getDoorTex(door) {
  const { opened } = door;
  const base = { y: 261, width: 32, height: 32 };
  if (opened) {
    return { ...base, x: 199 };
  }
  const { kind } = door;
  const { id } = kind;
  switch (id) {
    case DOOR_KIND.wood:
      return { ...base, x: 1 };
    case DOOR_KIND.ebony:
      return { ...base, x: 34 };
    case DOOR_KIND.bronze:
      return { ...base, x: 67 };
    case DOOR_KIND.iron:
      return { ...base, x: 100 };
    case DOOR_KIND.silver:
      return { ...base, x: 133 };
    case DOOR_KIND.gold:
      return { ...base, x: 166 };
    default:
      return { ...base, x: 500 };
  }
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

function getWeaponText(weapon) {
  const { code } = weapon;
  switch (code) {
    case WEAPON_LIST_CODE.sword:
      return { x: 34, y: 479, width: 32, height: 32 };
    case WEAPON_LIST_CODE.knife:
    case WEAPON_LIST_CODE.bow:
    default:
      return { x: 1, y: 479, width: 32, height: 32 };
  }
}

export function getObjectTex(object) {
  const { type } = object;
  switch (type) {
    case TYPE_OBJECT.meleeWeapon:
    case TYPE_OBJECT.distanceWeapon:
      return getWeaponText(object);
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
