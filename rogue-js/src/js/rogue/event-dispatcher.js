class EventDispatcher {
  constructor(world) {
    this.world = world;
    this.joueur = world.joueur;
    this.aim = false;
  }

  pressUp() {
    if (!this.aim) {
      this.world.goUp();
    } else {
      this.world.joueur.aimUp();
    }
  }

  pressDown() {
    if (!this.aim) {
      this.world.goDown();
    } else {
      this.world.joueur.aimDown();
    }
  }

  pressLeft() {
    if (!this.aim) {
      this.world.goLeft();
    } else {
      this.world.joueur.aimLeft();
    }
  }

  pressRight() {
    if (!this.aim) {
      this.world.goRight();
    } else {
      this.world.joueur.aimRight();
    }
  }

  pressSpace() {
    if (!this.aim) {
      this.aim = true;
      this.joueur.isAiming = true;
      this.joueur.resetAim();
    } else {
      this.aim = false;
      this.joueur.isAiming = false;
      this.joueur.shoot(this.world);
      this.world.activate();
    }
  }
}

export default world => new EventDispatcher(world);
