import activate from "../activate-player";
import { filterInventory, getKeys } from "../player/inventory";
import { applyToObject } from "../objects/dungeon-objects";
import { TYPE_OBJECT } from "../objects";
import { updatePlayerView } from "../player";
import { canOpen, unlockedAndOpenDoor, switchDoor } from "../objects";
import PATTERNS from "../message-patterns";
import { computeDesc } from "../commons";
import { fillMessage, appendMessages, cleanPlayerAction } from "../commons";

/**
 *
 * @param {*} state
 * @param {*} door
 */
function openOrClose(state, door) {
  const { objects, player } = state;
  const newObjects = applyToObject(objects, door, function (d) {
    return switchDoor(d);
  });

  return {
    ...state,
    objects: newObjects,
    activate,
    player: { ...player, action: undefined },
  };
}

export function unlockDoor(state, key, door) {
  const { objects } = state;
  const newObjects = applyToObject(objects, door, function (d) {
    return unlockedAndOpenDoor(d);
  });
  const message = fillMessage(PATTERNS.keyOpenThis, {
    key: computeDesc(key),
    object: computeDesc(door),
  });

  return appendMessages(
    cleanPlayerAction({ ...state, objects: newObjects }),
    message
  );
}

function tryToUnlock(state, door) {
  const { player } = state;
  const { inventory } = player;
  const key = getKeys(inventory).reduce(function (a, key) {
    return canOpen(key, door) ? key : a;
  }, undefined);

  if (key) {
    return unlockDoor(state, key, door);
  }

  return cleanPlayerAction(state);
}

/**
 *
 * @param {*} state
 * @param {*} door
 */
function todo(state, door) {
  const { locked } = door;
  if (locked) {
    return updatePlayerView(tryToUnlock(state, door));
  }

  return updatePlayerView(openOrClose(state, door));
}

export default todo;
