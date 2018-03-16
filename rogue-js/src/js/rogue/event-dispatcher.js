import EventDispatcherWorld from "./dispatcher-world";
import EventDispatcherInventory from "./dispatcher-inventory";

class EventDispatcher {
  constructor(world) {
    this.world = world;
    this.dispatcherWorld = new EventDispatcherWorld(world);
    this.dispatcherInventory = new EventDispatcherInventory(world);
    this.currentDispatcher = this.dispatcherWorld;
  }

  gameIsFinished() {
    return this.dispatcherWorld.gameIsFinished();
  }

  nextStep() {
    this.dispatcherWorld.nextStep();
  }

  pressUp() {
    this.dispatcherWorld.pressUp();
  }

  pressDown() {
    this.dispatcherWorld.pressDown();
  }

  pressLeft() {
    this.dispatcherWorld.pressLeft();
  }

  pressRight() {
    this.dispatcherWorld.pressRight();
  }

  pressSpace() {
    this.dispatcherWorld.pressSpace();
  }

  pressEscape() {
    this.dispatcherWorld.pressEscape();
  }

  dispatchWorld() {
    this.currentDispatcher = this.dispatcherWorld;
  }

  dispatchInventory() {
    this.currentDispatcher = this.dispatcherInventory;
  }
}

export default world => new EventDispatcher(world);
