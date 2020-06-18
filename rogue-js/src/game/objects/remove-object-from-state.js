import {
  removeObjects as removeFromDungeon,
  isInDungeon,
} from "./dungeon-objects";
import {
  removeObject as removeFromInventory,
  isInInventory,
} from "../player/inventory";

function remove(state, object) {
  const { objects, player } = state;
  const { inventory } = player;

  if (isInDungeon(objects, object)) {
    return { ...state, objects: removeFromDungeon(objects, object) };
  }
  if (isInInventory(inventory, object)) {
    return {
      ...state,
      player: { ...player, inventory: removeFromInventory(inventory, object) },
    };
  }

  return state;
}

export default remove;
