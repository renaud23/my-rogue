import * as TILE from "./tile";

export default class Dungeon {
  constructor(lar, hau) {
    this.largeur = lar;
    this.hauteur = hau;
    this.map = [];
    this.init();
  }

  init() {
    for (let i = 0; i < this.largeur * this.hauteur; i++) {
      this.map[i] = TILE.WALL;
    }
  }

  fill(tile) {
    for (let i = 0; i < this.largeur * this.hauteur; i++) {
      this.map[i] = tile;
    }
  }

  peekRandomOne(tile) {
    let i = 0;
    while (this.map[i].value !== tile.value) {
      i = Math.floor(Math.random() * Math.floor(this.largeur * this.hauteur));
    }

    return { x: i % this.largeur, y: Math.trunc(i / this.largeur) };
  }

  getTile(i, j) {
    return this.map[i + j * this.largeur];
  }

  setTile(i, j, value) {
    this.map[i + j * this.largeur] = value;
  }

  getLargeur() {
    return this.largeur;
  }

  getHauteur() {
    return this.hauteur;
  }
}
