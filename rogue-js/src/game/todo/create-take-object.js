import { cleanPlayerAction } from "../commons";
import { putObject } from "../player/inventory";

function createPrendreObjectTodo(object) {
  return function (state) {
    const { player, dungeon } = state;
    const { currentLevel, inventory } = player;
    dungeon.removeObject(currentLevel, object);
    return cleanPlayerAction({
      ...state,
      player: { ...player, inventory: putObject(inventory, object) },
    });
  };
}

export default createPrendreObjectTodo;
