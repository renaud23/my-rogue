import * as tools from "js/rogue/tools";
import Dungeon from "./../world/dungeon";
import * as TILE from "./../world/tile";
import { createKnife } from "js/rogue/weapon";

class Joueur {
  constructor(x, y) {
    this.x = x;
    this.y = y;
    this.isAiming = false;
    this.aimx = x;
    this.aimy = y;
    this.depht = 12;
    this.weapon = createKnife();
  }

  getMemory() {
    return this.memory;
  }

  getVisibilityPoints(world) {
    let points = [];
    if (!this.memory) {
      this.memory = new Dungeon(world.getLargeur(), world.getHauteur());
      this.memory.fill(TILE.UNKNOW);
    }
    for (let i = -this.depht; i < this.depht; i++) {
      for (let j = -this.depht; j < this.depht; j++) {
        const x = this.x + i;
        const y = this.y + j;
        if (tools.isInCicle(this.x, this.y, this.depht, x, y)) {
          if (world.canSee(this, x, y)) {
            this.memory.setTile(x, y, world.getTile(x, y));
            points.push({ x, y });
          }
        }
      }
    }

    return points;
  }

  getX() {
    return this.x;
  }

  getY() {
    return this.y;
  }

  goUp() {
    this.y--;
  }
  goDown() {
    this.y++;
  }
  goLeft() {
    this.x--;
  }
  goRight() {
    this.x++;
  }

  resetAim() {
    this.aimx = this.x;
    this.aimy = this.y;
  }
  aimUp() {
    const aimy = this.aimy - 1;
    if (Math.abs(this.y - aimy) <= this.weapon.depht) {
      this.aimy--;
    }
  }
  aimDown() {
    const aimy = this.aimy + 1;
    if (Math.abs(this.y - aimy) <= this.weapon.depht) {
      this.aimy++;
    }
  }
  aimLeft() {
    const aimx = this.aimx - 1;
    if (Math.abs(this.x - aimx) <= this.weapon.depht) {
      this.aimx--;
    }
  }
  aimRight() {
    const aimx = this.aimx + 1;
    if (Math.abs(this.x - aimx) <= this.weapon.depht) {
      this.aimx++;
    }
  }

  shoot() {}

  isIn(x, y) {
    return this.x === x && this.y === y;
  }

  isPlayer() {
    return true;
  }
}

export default (x, y) => {
  return new Joueur(x, y);
};
