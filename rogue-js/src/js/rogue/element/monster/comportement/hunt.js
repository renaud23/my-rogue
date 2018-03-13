import { createGoTo } from "js/rogue/element/monster/comportement";

class HuntPlayer {
  constructor(monster) {
    this.monster = monster;
    this.goTo = null;
    this.isFinished = false;
  }

  activate(world) {
    if (this.goTo === null) {
      this.goTo = createGoTo(this.monster, world.joueur.x, world.joueur.y); //new GoTo(monster, world.getJoueur().getX(), world.getJoueur().getY());
    } else if (world.canSeePlayer(this.monster)) {
      this.goTo.reset(world.joueur.x, world.joueur.y);
    } else {
      this.isFinished = true;
    }

    this.goTo.activate(world);
  }

  reset() {
    this.isFinished = false;
  }

  isFinished() {
    return this.isFinished;
  }
}

export default monster => new HuntPlayer(monster);
