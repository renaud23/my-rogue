import { createNavigate } from "../commons";
import { TYPE_OBJECT } from "../objects";
import { PLAYER_ACTIONS } from "../../commons";
import PATTERNS from "../message-patterns";
import activate from "../activate-player";
import { fillMessage } from "../commons";
import {
  getObjectsAt,
  putObjects,
  removeObjects as removeFromDungeon,
} from "../objects/dungeon-objects";
import { removeObject as removeFRomInventory } from "../player/inventory";

function initializePlayer(player, key) {
  const { position } = player;
  return {
    ...player,
    action: {
      type: PLAYER_ACTIONS.navigate,
      position,
      color: "blue",
      active: -1,
      key,
    },
  };
}
function noChestHere(state) {
  const { player, messages } = state;
  return {
    ...state,
    player: { ...player, action: undefined },
    messages: [...messages, PATTERNS.thereIsNoChest],
    activate,
  };
}

function notAGoodChest(state) {
  const { player, messages } = state;
  return {
    ...state,
    player: { ...player, action: undefined },
    messages: [...messages, PATTERNS.notGoodChest],
    activate,
  };
}

function openChest(state, chest, key) {
  const { player, messages, objects } = state;
  const { inventory } = player;
  const nextInventory = putObjects(
    removeFRomInventory(inventory, key),
    ...chest.loot()
  );
  return {
    ...state,
    objects: removeFromDungeon(objects, chest),
    player: {
      ...player,
      inventory: nextInventory,
      action: undefined,
    },
    messages: [...messages, fillMessage(PATTERNS.chestOpened, { chest })],
    activate,
  };
}

function useKey(state, event) {
  const { player, objects, messages } = state;
  const { action, currentLevel } = player;
  const { position, key } = action;
  const chest = getObjectsAt(objects, currentLevel, position).find(function (
    object
  ) {
    const { type } = object;
    return type === TYPE_OBJECT.chest;
  });

  if (!chest) {
    return noChestHere(state);
  }
  if (chest.kind !== key.target) {
    return notAGoodChest(state);
  }

  return openChest(state, chest, key);
}

function navigate(state, key) {
  const { player } = state;
  return {
    ...state,
    player: initializePlayer(player, key),
    activate: createNavigate(useKey, 1),
  };
}

export default navigate;
