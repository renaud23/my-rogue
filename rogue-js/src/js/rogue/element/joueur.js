import * as tools from "js/rogue/tools";
import Dungeon from "./../world/dungeon";
import * as TILE from "./../world/tile";

class Joueur {
  constructor(x, y) {
    this.x = x;
    this.y = y;
    this.depht = 12;
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
          if (world.canSee(x, y)) {
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
}

export default (x, y) => {
  return new Joueur(x, y);
};
