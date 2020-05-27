import { removeObject } from "../player/inventory";
import { putObjectDungeon } from "../objects";

function throwObjectTodo(state) {
  const { player, objects } = state;
  const { inventory, currentLevel, position, action } = player;
  const { object } = action;

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
}

export default throwObjectTodo;
