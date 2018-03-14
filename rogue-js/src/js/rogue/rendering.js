import { TILE } from "js/rogue";

class Rendering {
  constructor(world, largeurView, hauteurView) {
    this.world = world;
    this.largeurView = largeurView;
    this.hauteurView = hauteurView;
  }

  getWorld() {
    return this.world;
  }
  getFrame() {
    const map = [];
    let startX = Math.max(0, this.world.joueur.x - Math.trunc(this.largeurView / 2));
    startX = Math.min(this.world.getLargeur() - this.largeurView, startX);
    let startY = Math.max(0, this.world.joueur.y - Math.trunc(this.hauteurView / 2));
    startY = Math.min(this.world.getHauteur() - this.hauteurView, startY);
    for (let i = 0; i < this.hauteurView; i++) {
      for (let j = 0; j < this.largeurView; j++) {
        map[i * this.largeurView + j] = TILE.UNKNOW;
        if (this.world.joueur.getMemory()) {
          const tile = this.world.joueur.getMemory().getTile(startX + j, startY + i);
          let color = TILE.UNKNOW.color;
          map[i * this.largeurView + j] = { value: tile.value, color, render: tile.render };
        }
      }
    }
    // joueur
    let posX = Math.min(this.world.joueur.x, Math.trunc(this.largeurView / 2));
    posX = Math.max(posX, this.largeurView - this.world.getLargeur() + this.world.joueur.x);
    let posY = Math.min(this.world.joueur.y, Math.trunc(this.hauteurView / 2));
    posY = Math.max(posY, this.hauteurView - this.world.getHauteur() + this.world.joueur.y);

    const visibility = this.world.joueur.getVisibilityPoints(this.world);
    visibility.forEach(p => {
      let xi = posX + p.x - this.world.joueur.x;
      let yi = posY + p.y - this.world.joueur.y;

      if (xi >= 0 && yi >= 0 && xi < this.largeurView && yi < this.hauteurView) {
        const pos = yi * this.largeurView + xi;
        map[pos] = this.world.getTile(p.x, p.y);
      }
    });

    map[posY * this.largeurView + posX] = TILE.JOUEUR;

    // monsters
    for (let i = 0; i < this.world.monsters.length; i++) {
      let monster = this.world.monsters[i];
      for (let j = 0; j < visibility.length; j++) {
        if (monster.x === visibility[j].x && monster.y === visibility[j].y) {
          const xi = monster.x - startX;
          const yi = monster.y - startY;

          map[yi * this.largeurView + xi] = monster.getTile();
          break;
        }
      }
    }

    // aim
    if (this.world.joueur.isAiming) {
      const xi = this.world.joueur.aimx - startX;
      const yi = this.world.joueur.aimy - startY;
      if (xi >= 0 && yi >= 0 && xi < this.largeurView && yi < this.hauteurView) {
        map[yi * this.largeurView + xi] = TILE.AIM;
      }
    }

    return map;
  }

  getLargeur() {
    return this.largeurView;
  }

  getHauteur() {
    return this.hauteurView;
  }
}

export default (world, largeur, hauteur) => {
  return new Rendering(world, largeur, hauteur);
};
