import { removeObject } from "../player/inventory";
import { putObjectDungeon } from "../objects";

function createThrowObjectTodo(object) {
  return function throwObject(state) {
    const { player, objects } = state;
    const { inventory, currentLevel, position } = player;

    const newObjects = putObjectDungeon(
      objects,
      { ...object, position },
      currentLevel
    );

    return {
      ...state,
      objects: newObjects,
      player: {
        ...player,
        inventory: removeObject(inventory, object),
        action: null,
      },
    };
  };
}

export default createThrowObjectTodo;
