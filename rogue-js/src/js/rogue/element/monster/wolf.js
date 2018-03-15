import { TILE } from "js/rogue";
import { createRandomWalk, createHuntPlayer } from "./comportement";

class Wolf {
  constructor(x, y) {
    this.x = x;
    this.y = y;
    this.speed = 3;
    this.depht = 8;
    this.life = 10;
    this.damage = 5;
    this.xp = 2;
    this.randomWalk = createRandomWalk(this);
    this.huntPlayer = createHuntPlayer(this);
    this.isHuting = false;
  }

  activate(world) {
    for (let step = 0; step < this.speed; step++) {
      if (this.isHuting) {
        if (Math.abs(this.x - world.joueur.x) <= 1 && Math.abs(this.y - world.joueur.y) <= 1) {
          world.joueur.injure(this.damage);
        } else {
          this.huntPlayer.activate(world);
        }
      } else {
        if (world.canSeePlayer(this)) {
          this.isHuting = true;
          this.huntPlayer.reset();
          this.activate(world);
        } else {
          this.randomWalk.activate(world);
        }
      }
    }
  }

  isDead() {
    return this.life <= 0;
  }

  isIn(x, y) {
    return this.x === x && this.y === y;
  }

  getTile() {
    return TILE.WOLF;
  }

  isPlayer() {
    return false;
  }

  isOpaque() {
    return false;
  }
}

export default Wolf;
