import { cleanPlayerAction } from "../commons";
import { putObject, hasEnoughSpaceFor } from "../player/inventory";
import { removeObjectDungeon } from "../objects";

function createPrendreObjectTodo(object) {
  return function (state) {
    const { player, objects } = state;
    const { size } = object;
    const { currentLevel, inventory } = player;

    if (hasEnoughSpaceFor(inventory, size)) {
      const newObjects = removeObjectDungeon(objects, object, currentLevel);
      return cleanPlayerAction({
        ...state,
        objects: newObjects,
        player: { ...player, inventory: putObject(inventory, object) },
      });
    }
    // TODO msg inventaire plein
    return { ...state, player: { ...player, action: null } };
  };
}

export default createPrendreObjectTodo;
