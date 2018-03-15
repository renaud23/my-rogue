import Wolf from "./monster/wolf";
import { createGhoul } from "./monster/shooter";

export const MONSTER_TYPE = { WOLF: "WOLF", GHOUL: "GHOUL" };

export class Monster {
  constructor() {
    this.x = 0;
    this.y = 0;
    this.speed = 0;
    this.depht = 0;
    this.life = 0;
    this.damage = 0;
    this.xp = 0;
  }

  activate(world) {}

  isDead() {
    return false;
  }

  isIn(x, y) {
    return false;
  }

  getTile() {
    return null;
  }

  isPlayer() {
    return false;
  }

  isOpaque() {
    return true;
  }
}

export const createMonster = (type, x, y) => {
  switch (type) {
    case MONSTER_TYPE.WOLF:
      return new Wolf(x, y);
    case MONSTER_TYPE.GHOUL:
      return createGhoul(x, y);
    default:
      return null;
  }
};
