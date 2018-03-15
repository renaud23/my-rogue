import { TILE } from "js/rogue";
import * as tools from "js/rogue/tools";
import { journal } from "js/rogue";

class Projectile {
  constructor(x, y, ciblex, cibley) {
    this.x = x;
    this.y = y;
    this.speed = 2;
    this.depht = 8;
    this.finished = false;

    this.pente = (cibley - y) / Math.max(1, ciblex - x);
    this.varx = (ciblex - x) / Math.max(1, Math.abs(ciblex - x));

    this.nx = this.x + Math.round(this.varx * this.depht);
    this.ny = this.y + Math.round(this.pente * this.depht);

    this.segment = tools.getSegment(this.x, this.y, this.nx, this.ny);
    this.segment.splice(this.depht);
  }

  activate(world) {
    for (let i = 0; i < this.speed; i++) {
      if (this.segment.length > 0) {
        const p = this.segment.splice(0, 1)[0];
        const element = world.getElement(p.x, p.y);

        if (element !== null) {
          this.finished = true;
          if (element.isPlayer()) {
            journal.addRow("Une boule de feu vous percute. brulant...");
          }
        } else if (!TILE.isWalkable(world.getTile(p.x, p.y))) {
          this.finished = true;
        } else {
          this.x = p.x;
          this.y = p.y;
        }
      } else {
        this.finished = true;
      }
    }
  }

  isFinished() {
    return this.finished;
  }

  getTile() {
    return TILE.FIRE_BALL;
  }
}

export default params => (x, y, ciblex, cibley) => {
  const projectile = new Projectile(x, y, ciblex, cibley);
  // projectile.speed = params.speed ? params.speed : 2;
  return projectile;
};
