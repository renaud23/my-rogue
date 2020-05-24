import { cleanPlayerAction } from "../commons";
import { putObject } from "../player/inventory";
import { removeObjectDungeon } from "../objects";

function createPrendreObjectTodo(object) {
  return function (state) {
    const { player, objects } = state;
    const { currentLevel, inventory } = player;
    const newObjects = removeObjectDungeon(objects, object, currentLevel);
    return cleanPlayerAction({
      ...state,
      objects: newObjects,
      player: { ...player, inventory: putObject(inventory, object) },
    });
  };
}

export default createPrendreObjectTodo;
