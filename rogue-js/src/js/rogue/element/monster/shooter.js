import { TILE } from "js/rogue";
import { createGoTo } from "js/rogue/element/monster/comportement";
import { createFireBall } from "js/rogue/projectile";
import * as tools from "js/rogue/tools";

class Shooter {
  constructor(x, y) {
    this.x = x;
    this.y = y;
    this.speed = 2;
    this.depht = 12;
    this.dephtOfFire = 10;
    this.life = 10;
    this.damage = 10;
    this.xp = 10;
    this.tile = null;
    this.fireBall = null;

    this.hasSeenPlayer = false;
    this.goTo = createGoTo(this, 0, 0);
  }

  activate(world) {
    if (this.fireBall !== null && this.fireBall.isFinished()) {
      this.fireBall = null;
    }
    //
    for (let step = 0; step < this.speed; step++) {
      const distToPlayer = tools.getDistance(this.x, this.y, world.joueur.x, world.joueur.y);
      if (this.hasSeenPlayer) {
        if (distToPlayer > this.depht * this.depht) {
          this.hasSeenPlayer = false;
        } else if (distToPlayer <= this.dephtOfFire * this.dephtOfFire) {
          if (world.canSeePlayer(this) && this.fireBall === null) {
            let dx = (world.joueur.x - this.x) / Math.max(1, Math.abs(world.joueur.x - this.x));
            let dy = (world.joueur.y - this.y) / Math.max(1, Math.abs(world.joueur.y - this.y));
            this.fireBall = createFireBall(this.x + dx, this.y + dy, world.joueur.x, world.joueur.y);
            world.addProjectile(this.fireBall);
            this.fireBall.activate(world);
          }
        } else {
          if (world.joueur.x !== this.nx || world.joueur.y !== this.ny) {
            if (world.canSeePlayer(this)) {
              this.nx = world.joueur.x;
              this.ny = world.joueur.y;
              this.goTo.reset(this.nx, this.ny);
            }
          }
          this.goTo.activate(world);
        }
      } else {
        if (distToPlayer <= this.depht * this.depht) {
          if (world.canSeePlayer(this)) {
            this.hasSeenPlayer = true;
            this.nx = world.joueur.x;
            this.ny = world.joueur.y;
            this.goTo.reset(this.nx, this.ny);
          }
        }
      }
    } // for
  }

  isDead() {
    return this.life <= 0;
  }

  isIn(x, y) {
    return this.x === x && this.y === y;
  }

  getTile() {
    return this.tile;
  }

  isPlayer() {
    return false;
  }

  isOpaque() {
    return false;
  }
}

const createShooter = params => (x, y) => {
  const monster = new Shooter(x, y);
  monster.speed = params.speed;
  monster.depht = params.depht;
  monster.dephtOfFire = params.dephtOfFire;
  monster.life = params.life;
  monster.damage = params.damage;
  monster.xp = params.xp;
  monster.tile = params.tile;
  // TODO
  return monster;
};

export const createGhoul = createShooter({
  speed: 2,
  depht: 12,
  dephtOfFire: 6,
  life: 10,
  damage: 10,
  xp: 10,
  tile: TILE.GHOUL
});
