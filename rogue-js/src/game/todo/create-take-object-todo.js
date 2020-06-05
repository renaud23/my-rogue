import { cleanPlayerAction } from "../commons";
import { putObject, hasEnoughSpaceFor } from "../player/inventory";
import { removeObjectDungeon } from "../objects";
import { fillMessage } from "../commons";
import PATTERNS from "../message-patterns";
import { consumeMove } from "../commons";

function createPrendreObjectTodo(object) {
  return function (state) {
    const { player, objects, messages } = state;
    const { size } = object;
    const { currentLevel, inventory } = player;

    if (hasEnoughSpaceFor(inventory, size)) {
      const newObjects = removeObjectDungeon(objects, object, currentLevel);
      const newMsg = [
        ...messages,
        fillMessage(PATTERNS.takeObject, { object }),
      ];
      return cleanPlayerAction({
        ...state,
        objects: newObjects,
        messages: newMsg,
        player: {
          ...consumeMove(player),
          inventory: putObject(inventory, object),
        },
      });
    }
    // TODO msg inventaire plein
    return {
      ...state,
      player: { ...player, action: undefined },
      messages: [...messages, PATTERNS.inventoryFull],
    };
  };
}

export default createPrendreObjectTodo;
