import { TYPE_OBJECT } from "../objects";
import { lookAtInventory } from "../player/inventory";
import { fillMessage } from "../commons";
import PATTERNS from "../message-patterns";
import activate from "../activate-player";
import { openChest } from "./use-key-todo";

function youNeedAKey(state, chest) {
  const { player, messages } = state;
  return {
    ...state,
    player: { ...player, action: undefined },
    messages: [...messages, fillMessage(PATTERNS.needAKey, { chest })],
    activate,
  };
}

function tryToOpenChest(state, chest) {
  const { player } = state;
  const { kind } = chest;
  const { inventory } = player;
  const key = lookAtInventory(inventory, function (o) {
    const { target, type } = o;
    return type === TYPE_OBJECT.key && target === kind;
  });
  if (!key) {
    return youNeedAKey(state, chest);
  }
  return openChest(state, chest, key);
}

export default tryToOpenChest;
