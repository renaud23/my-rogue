import { cleanPlayerAction } from "../commons";
import { putObject, hasEnoughSpaceFor } from "../player/inventory";
import { aggregateObjects } from "../objects";
import { removeObjects as removeFromDungeon } from "../objects/dungeon-objects";
import { fillMessage } from "../commons";
import PATTERNS from "../message-patterns";
import { lookAtInventory, replaceObject } from "../player/inventory";
import { consumeMove } from "../commons";
import { computeDesc } from "../commons";

function lookForMerge(inventory, object) {
  const { code } = object;
  const same = lookAtInventory(inventory, function (o) {
    return o.code === code;
  });

  if (same) {
    return replaceObject(inventory, same, aggregateObjects(same, object));
  }
  return putObject(inventory, object);
}

function takeAndAggregate(state, object) {
  const { player, objects, messages } = state;
  const { code } = object;
  const { currentLevel, inventory } = player;

  const nextObjects = removeFromDungeon(objects, object);
  const nextInventory = lookForMerge(inventory, object);

  const nextMessages = [
    ...messages,
    fillMessage(PATTERNS.takeObject, { desc: computeDesc(object) }),
  ];

  return cleanPlayerAction({
    ...state,
    messages: nextMessages,
    objects: nextObjects,
    player: { ...player, inventory: nextInventory },
  });
}

function takeSingle(state, object) {
  const { player, objects, messages } = state;
  const { inventory } = player;
  const newMsg = [
    ...messages,
    fillMessage(PATTERNS.takeObject, { desc: computeDesc(object) }),
  ];
  return cleanPlayerAction({
    ...state,
    objects: removeFromDungeon(objects, object),
    messages: newMsg,
    player: {
      ...consumeMove(player),
      inventory: putObject(inventory, {
        ...object,
        level: undefined,
        position: undefined,
      }),
    },
  });
}

function takeObjectTodo(state, object) {
  const { player, messages } = state;
  const { size, aggregative } = object;
  const { inventory } = player;

  if (hasEnoughSpaceFor(inventory, size)) {
    if (aggregative) {
      return takeAndAggregate(state, object);
    }
    return takeSingle(state, object);
  }
  return {
    ...state,
    player: { ...player, action: undefined },
    messages: [...messages, PATTERNS.inventoryFull],
  };
}

export default takeObjectTodo;
