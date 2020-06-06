import { cleanPlayerAction } from "../commons";
import { putObject, hasEnoughSpaceFor } from "../player/inventory";
import { removeObjectDungeon, aggregateObjects } from "../objects";
import { fillMessage } from "../commons";
import PATTERNS from "../message-patterns";
import { consumeMove } from "../commons";

function takeAndAggregate(object, state) {
  const { player, objects, messages } = state;
  const { size, code } = object;
  const { currentLevel, inventory } = player;
  const { objects: invObj } = inventory;

  const same = invObj.find(function (o) {
    return o.code === code;
  });
  if (same) {
    const newMsg = [...messages, fillMessage(PATTERNS.takeObject, { object })];
    const newObjects = removeObjectDungeon(objects, object, currentLevel);
    const newInventoryObjects = invObj.map(function (o) {
      const { id, how } = o;
      if (id === same.id) {
        return aggregateObjects(same, object);
      }
      return o;
    });

    return cleanPlayerAction({
      ...state,
      objects: newObjects,
      messages: newMsg,
      player: {
        ...consumeMove(player),
        inventory: { ...inventory, objects: newInventoryObjects },
      },
    });
  }

  return takeSingle(object, state);
}

function takeSingle(object, state) {
  const { player, objects, messages } = state;
  const { currentLevel, inventory } = player;
  const newObjects = removeObjectDungeon(objects, object, currentLevel);
  const newMsg = [...messages, fillMessage(PATTERNS.takeObject, { object })];
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

function createPrendreObjectTodo(object) {
  return function (state) {
    const { player, messages } = state;
    const { size, aggregative } = object;
    const { inventory } = player;

    if (hasEnoughSpaceFor(inventory, size)) {
      if (aggregative) {
        return takeAndAggregate(object, state);
      }
      return takeSingle(object, state);
    }
    return {
      ...state,
      player: { ...player, action: undefined },
      messages: [...messages, PATTERNS.inventoryFull],
    };
  };
}

export default createPrendreObjectTodo;
