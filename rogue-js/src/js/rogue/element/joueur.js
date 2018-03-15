import * as tools from "js/rogue/tools";
import Dungeon from "./../world/dungeon";
import * as TILE from "./../world/tile";
import { createKnife } from "js/rogue/weapon";

let REFRESH = () => {};
class Joueur {
  constructor(x, y) {
    this.x = x;
    this.y = y;
    this.isAiming = false;
    this.aimx = x;
    this.aimy = y;
    this.depht = 12;
    this.level = 1;
    this.xp = 0;
    this.lifeMax = Joueur.LIFE_MAX;
    this.life = Joueur.LIFE_MAX;
    this.weapon = createKnife();
  }

  setRefresh(refresh) {
    REFRESH = refresh;
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
    REFRESH();
  }
  aimUp() {
    const aimy = this.aimy - 1;
    if (Math.abs(this.y - aimy) <= this.weapon.depht) {
      this.aimy--;
      REFRESH();
    }
  }
  aimDown() {
    const aimy = this.aimy + 1;
    if (Math.abs(this.y - aimy) <= this.weapon.depht) {
      this.aimy++;
      REFRESH();
    }
  }
  aimLeft() {
    const aimx = this.aimx - 1;
    if (Math.abs(this.x - aimx) <= this.weapon.depht) {
      this.aimx--;
      REFRESH();
    }
  }
  aimRight() {
    const aimx = this.aimx + 1;
    if (Math.abs(this.x - aimx) <= this.weapon.depht) {
      this.aimx++;
      REFRESH();
    }
  }

  shoot(world) {
    for (let i = 0; i < world.monsters.length; i++) {
      const monster = world.monsters[i];
      if (monster.isIn(this.aimx, this.aimy)) {
        monster.life -= this.weapon.damage * this.level;
        this.bloody(world, this.aimx, this.aimy);
        if (monster.isDead()) {
          this.xp += monster.xp;
          this.checkLevel();
          world.setTile(this.aimx, this.aimy, TILE.BODY);
        }
      }
    }
    REFRESH();
  }

  checkLevel() {
    if (this.xp > this.howForNextLevel()) {
      this.level++;
      this.xp = 0;
    }
  }

  howForNextLevel() {
    return Math.trunc(Math.pow(this.level, 1.5)) * 20;
  }

  bloody(world, x, y) {
    for (let i = -1; i <= 1; i++) {
      for (let j = -1; j <= 1; j++) {
        const how = Math.trunc(Math.random() * 100);
        if (how === 90 && TILE.isWalkable(world.getTile(this.aimx + i, this.aimy + j))) {
          world.setTile(this.aimx + i, this.aimy + j, TILE.BODY_PART);
        } else if (how > 80) {
          world.setColor(this.aimx + i, this.aimy + j, "blood");
        }
      }
    }
  }

  isIn(x, y) {
    return this.x === x && this.y === y;
  }

  injure(how) {
    this.life -= how;
  }

  isDead() {
    return this.life < 0;
  }

  isPlayer() {
    return true;
  }
}

Joueur.LIFE_MAX = 100;

export default (x, y) => {
  return new Joueur(x, y);
};
