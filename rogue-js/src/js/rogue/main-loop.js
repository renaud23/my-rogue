import * as TILE from "js/rogue";

class MainLoop {
  constructor(world, largeurView, hauteurView) {
    this.world = world;
    this.largeurView = largeurView;
    this.hauteurView = hauteurView;
  }

  getFrame() {
    const map = [];
    let startX = Math.max(0, this.world.joueur.x - Math.trunc(this.largeurView / 2));
    startX = Math.min(this.world.getLargeur() - this.largeurView, startX);
    let startY = Math.max(0, this.world.joueur.y - Math.trunc(this.hauteurView / 2));
    startY = Math.min(this.world.getHauteur() - this.hauteurView, startY);
    for (let i = 0; i < this.hauteurView; i++) {
      for (let j = 0; j < this.largeurView; j++) {
        map[i * this.largeurView + j] =  TILE.UNKNOW;
        if (this.world.joueur.getMemory()) {
          const tile = this.world.joueur.getMemory().getTile(startX + j, startY + i);
          let color =  TILE.UNKNOW.color;
          map[i * this.largeurView + j] =  {value: tile.value, color };
        }
      }
    }

    let posX = Math.min(this.world.joueur.x, Math.trunc(this.largeurView / 2));
    posX = Math.max(posX, this.largeurView - this.world.getLargeur() + this.world.joueur.x);
    let posY = Math.min( this.world.joueur.y, Math.trunc(this.hauteurView / 2));
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

    return map;
  }

  getLargeur() {
    return this.largeurView;
  }

  getHauteur() {
    return this.hauteurView;
  }

  pressUp() {
    this.world.goUp();
  }
  pressDown() {
    this.world.goDown();
  }
  pressLeft() {
    this.world.goLeft();
  }
  pressRight() {
    this.world.goRight();
  }
  pressSpace() {}

  getWorld(){
    return this.world;
  }
}

export default (world, largeur, hauteur) => {
  return new MainLoop(world, largeur, hauteur);
};
