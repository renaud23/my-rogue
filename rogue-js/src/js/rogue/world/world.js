import smoothDungeon from "./smooth-dungeon";
import createJoueur from "./../element/joueur";
import * as tools from "js/rogue/tools";
import * as TILE from "./tile";

class World {
  constructor(largeur, hauteur) {
    this.largeur = largeur;
    this.hauteur = hauteur;
    this.monsters = [];
    this.dungeon = smoothDungeon(largeur, hauteur).build();
    const pos = this.dungeon.peekRandomOne(TILE.FLOOR);
    this.joueur = createJoueur(pos.x, pos.y);
  }

  activate() {
    // next turn
    this.monsters.forEach(m => {
      m.activate(this);
    });
  }

  canGo(x, y) {
    if (x < 0 || y < 0 || x >= this.largeur || y >= this.hauteur) return false;
    if (this.getTile(x, y).value === TILE.WALL.value) return false;
    for (let i = 0; i < this.monsters.length; i++) {
      if (this.monsters[i].isIn(x, y)) return false;
    }
    return true;
  }

  elementCanGo(e, x, y) {
    if (x < 0 || y < 0 || x >= this.dungeon.getLargeur() || y >= this.dungeon.getHauteur()) {
      return false;
    }
    if (this.dungeon.getTile(x, y).value !== TILE.FLOOR.value) {
      return false;
    }
    if (this.joueur.isIn(x, y) && !e.isPlayer()) return false;
    for (let i = 0; i < this.monsters.length; i++) {
      if (this.monsters[i].isIn(x, y)) {
        return false;
      }
    }
    return true;
  }

  canSeeThrough(e, x, y) {
    if (x < 0 || y < 0 || x >= this.dungeon.getLargeur() || y >= this.dungeon.getHauteur()) {
      return false;
    }
    if (this.dungeon.getTile(x, y).value !== TILE.FLOOR.value) {
      return false;
    }
    for (let i = 0; i < this.monsters.length; i++) {
      if (e === this.monsters[i]) {
        continue;
      }
      if (this.monsters[i].isIn(x, y) && this.monsters[i].isOpaque()) {
        return false;
      }
    }
    return true;
  }

  canSeePlayer(monster) {
    if (tools.getDistance(monster.x, monster.y, this.joueur.x, this.joueur.y) <= monster.depht * monster.depht) {
      const points = tools.getSegment(monster.x, monster.y, this.joueur.x, this.joueur.y);
      for (let i = 0; i < points.length; i++) {
        if (!this.canSeeThrough(monster, this.joueur.x, this.joueur.y)) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  elementCanGoTo(e, x1, y1, x2, y2) {
    if (!this.elementCanGo(e, x2, y2)) return false;
    const points = tools.getSegment(x1, y1, x2, y2);

    for (let i = 0; i < points.length; i++) {
      const w = points[i];
      if (!e.isIn(w.x, w.y)) {
        if (!this.elementCanGo(e, w.x, w.y)) {
          return false;
        }
      }
    }

    return true;
  }

  canSee(e, x, y) {
    if (x < 0 || y < 0 || x >= this.largeur || y >= this.hauteur) return false;
    let can = true;

    tools.getSegment(this.joueur.x, this.joueur.y, x, y).forEach(p => {
      if (x === p.x && y === p.y) {
      } else {
        if (!this.canSeeThrough(e, p.x, p.y)) {
          can = false;
          return;
        }
      }
    });
    return can;
  }

  peekRandomPlace() {
    let x, y;
    do {
      x = Math.trunc(Math.random() * this.getLargeur());
      y = Math.trunc(Math.random() * this.getHauteur());
    } while (!this.canGo(x, y));
    return { x, y };
  }

  addMonster(monster) {
    if (typeof monster.activate === "function") {
      this.monsters.push(monster);
    }
  }

  getLargeur() {
    return this.largeur;
  }

  getHauteur() {
    return this.hauteur;
  }

  getTile(i, j) {
    return this.dungeon.getTile(i, j);
  }

  getJoueur() {
    return this.joueur;
  }

  setTile(i, j, value) {}

  goUp() {
    if (this.canGo(this.joueur.x, this.joueur.y - 1)) {
      this.joueur.goUp();
      this.activate();
    }
  }
  goDown() {
    if (this.canGo(this.joueur.x, this.joueur.y + 1)) {
      this.joueur.goDown();
      this.activate();
    }
  }
  goLeft() {
    if (this.canGo(this.joueur.x - 1, this.joueur.y)) {
      this.joueur.goLeft();
      this.activate();
    }
  }
  goRight() {
    if (this.canGo(this.joueur.x + 1, this.joueur.y)) {
      this.joueur.goRight();
      this.activate();
    }
  }

  shoot() {
    console.log("shoot !");
    this.activate();
  }
}

export const createWorld = (largeur, hauteur) => new World(largeur, hauteur);
