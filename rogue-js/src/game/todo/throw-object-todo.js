import { removeObject } from "../player/inventory";
import { putObjectDungeon } from "../objects";
import { fillMessage } from "../commons";
import PATTERNS from "../message-patterns";

function throwObjectTodo(state) {
  const { player, objects, messages } = state;
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
    messages: [...messages, fillMessage(PATTERNS.throwObject, { object })],
    player: {
      ...player,
      inventory: removeObject(inventory, object),
      action: null,
    },
  };
}

export default throwObjectTodo;
