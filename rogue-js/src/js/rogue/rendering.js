import RenderingWorld from "./rendering-world";

class Rendering {
  constructor(world, largeurView, hauteurView) {
    this.renderingWorld = new RenderingWorld(world, largeurView, hauteurView);
    this.currentRendering = this.renderingWorld;
  }

  isWin() {
    return this.currentRendering.isWin();
  }

  isLose() {
    return this.currentRendering.isLose();
  }

  getWorld() {
    return this.currentRendering.getWorld();
  }
  getFrame() {
    return this.currentRendering.getFrame();
  }

  getLargeur() {
    return this.currentRendering.getLargeur();
  }

  getHauteur() {
    return this.currentRendering.getHauteur();
  }
}

export default (world, largeur, hauteur) => {
  return new Rendering(world, largeur, hauteur);
};
