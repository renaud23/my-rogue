class EventDispatcher {
  constructor(world) {
    this.world = world;
    this.joueur = world.joueur;
    this.aim = false;
    this.nextAsk = false;
  }

  gameIsFinished() {
    return this.world.lose || this.world.win;
  }

  nextStep() {
    if (!this.nextAsk) {
      this.nextAsk = true;
    } else {
      this.nextAsk = false;
      this.world.goNextStep();
    }
  }

  pressUp() {
    this.nextAsk = false;
    if (!this.aim) {
      this.world.goUp();
    } else {
      this.world.joueur.aimUp();
    }
  }

  pressDown() {
    this.nextAsk = false;
    if (!this.aim) {
      this.world.goDown();
    } else {
      this.world.joueur.aimDown();
    }
  }

  pressLeft() {
    this.nextAsk = false;
    if (!this.aim) {
      this.world.goLeft();
    } else {
      this.world.joueur.aimLeft();
    }
  }

  pressRight() {
    this.nextAsk = false;
    if (!this.aim) {
      this.world.goRight();
    } else {
      this.world.joueur.aimRight();
    }
  }

  pressSpace() {
    this.nextAsk = false;
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
