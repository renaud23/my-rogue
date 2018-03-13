class EventDispatcher {
  constructor(world) {
    this.world = world;
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
}

export default world => new EventDispatcher(world);
