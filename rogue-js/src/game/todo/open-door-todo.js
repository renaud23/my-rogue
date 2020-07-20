import activate from "../activate-player";
import { filterInventory } from "../player/inventory";
import { applyToObject } from "../objects/dungeon-objects";
import { TYPE_OBJECT } from "../objects";
import { updatePlayerView } from "../player";

/**
 *
 * @param {*} state
 * @param {*} door
 */
function openOrClose(state, door) {
  const { objects, player } = state;
  const newObjects = applyToObject(objects, door, function (d) {
    const { opened } = d;
    if (opened) {
      return { ...d, opened: false };
    }
    return { ...d, opened: true };
  });

  return {
    ...state,
    objects: newObjects,
    activate,
    player: { ...player, action: undefined },
  };
}

function unlockDoor(state, door) {
  const { objects, player } = state;
  const newObjects = applyToObject(objects, door, function (d) {
    return { ...d, locked: false };
  });

  return {
    ...state,
    objects: newObjects,
    activate,
    player: { ...player, activate, action: undefined },
  };
}

function tryToUnlock(state, door) {
  const { player } = state;
  const { inventory } = player;
  const { kind } = door;
  const keys = filterInventory(inventory, function (o) {
    const { type } = o;
    if (type === TYPE_OBJECT.key) {
      const { target } = o;
      return target === kind;
    }
    return false;
  });

  if (keys.length) {
    return unlockDoor(state, door);
  }
  return {
    ...state,
    activate,
    player: { ...player, activate, action: undefined },
  };
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
