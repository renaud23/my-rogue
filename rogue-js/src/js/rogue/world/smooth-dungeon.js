import Dungeon from "./dungeon";
import * as TILE from "./tile";

class DungeonBuilder {
  constructor(lar, hau) {
    this.dungeon = new Dungeon(lar, hau);
    this.step = 5;
    this.init();
  }

  init() {
    for (let i = 1; i < this.dungeon.getLargeur() - 1; i++) {
      for (let j = 1; j < this.dungeon.getHauteur() - 1; j++) {
        if (Math.random() * 100 > 45) {
          this.dungeon.setTile(i, j, TILE.FLOOR);
        }
      }
    }
  }

  carve() {
    const e2 = new Dungeon(this.dungeon.getLargeur(), this.dungeon.getHauteur());
    for (let i = 1; i < this.dungeon.getLargeur() - 1; i++) {
      for (let j = 1; j < this.dungeon.getHauteur() - 1; j++) {
        let nb = 0;
        // nb += e.get(i, j) != Tile.WALL ? 0 : 1;
        nb += this.dungeon.getTile(i - 1, j).value !== TILE.WALL.value ? 0 : 1;
        nb += this.dungeon.getTile(i + 1, j).value !== TILE.WALL.value ? 0 : 1;
        nb += this.dungeon.getTile(i, j - 1).value !== TILE.WALL.value ? 0 : 1;
        nb += this.dungeon.getTile(i, j + 1).value !== TILE.WALL.value ? 0 : 1;
        nb += this.dungeon.getTile(i - 1, j - 1).value !== TILE.WALL.value ? 0 : 1;
        nb += this.dungeon.getTile(i + 1, j + 1).value !== TILE.WALL.value ? 0 : 1;
        nb += this.dungeon.getTile(i - 1, j + 1).value !== TILE.WALL.value ? 0 : 1;
        nb += this.dungeon.getTile(i + 1, j - 1).value !== TILE.WALL.value ? 0 : 1;
        // http://www.roguebasin.com/index.php?title=Cellular_Automata_Method_for_Generating_Random_Cave-Like_Levels
        if (this.dungeon.getTile(i, j).value === TILE.WALL.value) {
          if (nb >= 4) {
            e2.setTile(i, j, TILE.WALL);
          } else if (nb < 2) {
            e2.setTile(i, j, TILE.FLOOR);
          } else {
            e2.setTile(i, j, TILE.FLOOR);
          }
        } else {
          if (nb >= 5) {
            e2.setTile(i, j, TILE.WALL);
          } else {
            e2.setTile(i, j, TILE.FLOOR);
          }
        }
      }
    }
    this.dungeon = e2;
  }

  build() {
    for (let i = 0; i < this.step; i++) {
      this.carve();
    }
    return this.dungeon;
  }
}

export default (largeur, hauteur) => {
  return new DungeonBuilder(largeur, hauteur);
};
