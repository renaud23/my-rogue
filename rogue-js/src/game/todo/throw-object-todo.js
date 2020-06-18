import { removeObject } from "../player/inventory";
import { putObjects } from "../objects/dungeon-objects";
import { fillMessage, computeDesc } from "../commons";
import { consumeMove } from "../commons";
import PATTERNS from "../message-patterns";

function throwObjectTodo(state, object) {
  const { player, objects, messages } = state;
  const { inventory, currentLevel, position } = player;

  const newObjects = putObjects(objects, {
    ...object,
    position,
    level: currentLevel,
  });

  return {
    ...state,
    objects: newObjects,
    messages: [
      ...messages,
      fillMessage(PATTERNS.throwObject, { desc: computeDesc(object) }),
    ],
    player: consumeMove({
      ...player,
      inventory: removeObject(inventory, object),
      action: undefined,
    }),
  };
}

export default throwObjectTodo;
