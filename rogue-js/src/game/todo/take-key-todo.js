import { cleanPlayerAction } from "../commons";
import { removeObjects } from "../objects/dungeon-objects";
import { putKeys } from "../player/inventory";

function takeIt(state, key) {
  const { objects, player } = state;
  const { inventory } = player;
  const newObjects = removeObjects(objects, key);
  const nextInventory = putKeys(inventory, key);

  return {
    ...state,
    objects: newObjects,
    player: { ...player, inventory: nextInventory },
  };
}

function todo(state, key) {
  return cleanPlayerAction(takeIt(state, key));
}

export default todo;
