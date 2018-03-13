import { createGoTo } from "js/rogue/element/monster/comportement";
import { getCircle } from "js/rogue/tools";

class RandomWalk {
  constructor(monster) {
    this.monster = monster;
    this.nx = monster.x;
    this.ny = monster.y;
    this.goTo = createGoTo(this.monster, monster.x, monster.y);
    this.reset();
  }

  checkNextDir(world) {
    let dx = this.monster.x;
    let dy = this.monster.y;

    let ray = this.monster.depht;
    let find = false;
    while (!find) {
      const points = getCircle(this.monster.x, this.monster.y, ray);
      while (points.length > 0) {
        const p = points.pop();

        if (world.elementCanGoTo(this.monster, this.monster.x, this.monster.y, p.x, p.y)) {
          find = true;
          dx = p.x;
          dy = p.y;
          break;
        }
      }
      if (!find) {
        ray--;
      }
    }
    this.goTo.reset(dx, dy);
  }

  reset() {
    this.nx = this.monster.x;
    this.ny = this.monster.y;
  }

  activate(world) {
    if (this.monster.speed > 0) {
      if (this.goTo.isFinished()) {
        this.checkNextDir(world);
      } else {
        this.goTo.activate(world);
      }
    }
  }

  isFinished() {
    return false;
  }
}

export default monster => new RandomWalk(monster);
