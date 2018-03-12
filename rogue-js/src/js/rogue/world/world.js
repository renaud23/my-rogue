import smoothDungeon from "./smooth-dungeon";
import createJoueur from "./../element/joueur";
import * as tools from "js/rogue/tools";
import * as TILE from "./tile";

class World {
  constructor(largeur, hauteur) {
    this.largeur = largeur;
    this.hauteur = hauteur;
    this.dungeon = smoothDungeon(largeur, hauteur).build();
    const pos = this.dungeon.peekRandomOne(TILE.FLOOR);
    this.joueur = createJoueur(pos.x, pos.y);
  }

  canGo(x, y) {
    if (x < 0 || y < 0 || x >= this.largeur || y >= this.hauteur) return false;
    if (this.getTile(x, y).value === TILE.WALL.value) return false;
    return true;
  }

  canSee(x, y) {
    if (x < 0 || y < 0 || x >= this.largeur || y >= this.hauteur) return false;
    let can = true;
    tools.getSegment(this.joueur.x, this.joueur.y, x, y).forEach(p => {
      if (x === p.x && y === p.y) {
      } else {
        if (this.getTile(p.x, p.y).value === TILE.WALL.value) {
          can = false;
          return;
        }
      }
    });
    return can;
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
    }
  }
  goDown() {
    if (this.canGo(this.joueur.x, this.joueur.y + 1)) {
      this.joueur.goDown();
    }
  }
  goLeft() {
    if (this.canGo(this.joueur.x - 1, this.joueur.y)) {
      this.joueur.goLeft();
    }
  }
  goRight() {
    if (this.canGo(this.joueur.x + 1, this.joueur.y)) {
      this.joueur.goRight();
    }
  }
}

export const createWorld = (largeur, hauteur) => new World(largeur, hauteur);
