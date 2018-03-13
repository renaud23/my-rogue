import { TILE } from "js/rogue";
import { createRandomWalk, createHuntPlayer } from "./comportement";

class Wolf {
  constructor(x, y) {
    this.x = x;
    this.y = y;
    this.speed = 1;
    this.depht = 8;
    this.randomWalk = createRandomWalk(this);
    this.huntPlayer = createHuntPlayer(this);
    this.isHuting = false;
  }

  activate(world) {
    if (this.isHuting) {
      this.huntPlayer.activate(world);
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

  isDead() {
    return false;
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
    return true;
  }
}

export default Wolf;
