import { TYPE_OBJECT, canOpen } from "../objects";
import { getKeys } from "../player/inventory";
import PATTERNS from "../message-patterns";
import { computeDesc } from "../commons";
import { removeObjects } from "../objects/dungeon-objects";
import { fillMessage, appendMessages, cleanPlayerAction } from "../commons";

export function unlockChest(state, key, chest) {
  const { objects } = state;
  const nextObjects = removeObjects(objects, chest);
  const message = fillMessage(PATTERNS.keyOpenThis, {
    key: computeDesc(key),
    object: computeDesc(chest),
  });
  return appendMessages(
    cleanPlayerAction({ ...state, objects: nextObjects }),
    message
  );
}

function lookForKey(state, chest) {
  const { player } = state;
  const { inventory } = player;
  return getKeys(inventory).reduce(function (a, key) {
    return canOpen(key, chest) ? key : a;
  }, undefined);
}

function tryToOpenChest(state, chest) {
  const key = lookForKey(state, chest);
  if (key) {
    return unlockChest(state, key, chest);
  }
  return cleanPlayerAction(state);
}

export default tryToOpenChest;
