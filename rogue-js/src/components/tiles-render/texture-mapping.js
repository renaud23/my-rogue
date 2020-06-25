import { TILES } from "../../commons";

import { WALL_CODES } from "../../game/commons/dungeon-tiles";

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
      return { ...base, x: 52, y: 163 };
    case WALL_CODES.CORNER_EAST:
      return { ...base, x: 86, y: 163 };
    case WALL_CODES.PILAR_WEST:
      return { ...base, x: 1, y: 163 };
    case WALL_CODES.PILAR_EAST:
      return { ...base, x: 35, y: 163 };
    // case WALL_CODES.NORTH_WEST:
    //   return { ...base, x: 32, y: 16 };
    // case WALL_CODES.NORTH:
    //   return { ...base, x: 16, y: 32 };
    // case WALL_CODES.NORTH_EAST:
    //   return { ...base, x: 32, y: 0 };
    // case WALL_CODES.EAST:
    //   return { ...base, x: 0, y: 16 };
    // case WALL_CODES.WEST:
    //   return { ...base, x: 32, y: 16 };
    // case WALL_CODES.SOUTH_WEST:
    //   return { ...base, x: 0, y: 48 };
    // case WALL_CODES.SOUTH:
    //   return { ...base, x: 16, y: 48 };
    // case WALL_CODES.SOUTH_EAST:
    //   return { ...base, x: 32, y: 32 };
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

export function getObjectTex(object) {
  return { x: 113, y: 65, width: 32, height: 32 };
}
