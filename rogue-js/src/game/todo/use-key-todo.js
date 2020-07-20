import { createNavigate } from "../commons";
import { canOpen, TYPE_OBJECT } from "../objects";
import { getObjectsAt } from "../objects/dungeon-objects";
import { PLAYER_ACTIONS } from "../../commons";
import { unlockDoor } from "./open-door-todo";
import PATTERNS from "../message-patterns";
import { appendMessages, cleanPlayerAction } from "../commons";

function initializePlayer(player, key) {
  const { position } = player;
  return {
    ...player,
    action: {
      type: PLAYER_ACTIONS.navigate,
      position,
      active: -1,
      key,
    },
  };
}

function useKey(state, event) {
  const { player, objects } = state;
  const { action, currentLevel } = player;
  const { position, key } = action;

  const objectToOpen = getObjectsAt(objects, currentLevel, position).find(
    function (o) {
      return canOpen(key, o);
    }
  );

  if (objectToOpen) {
    if (TYPE_OBJECT.door === objectToOpen.type) {
      return unlockDoor(state, key, objectToOpen);
    }
  }
  // Nothing to open with this key
  return appendMessages(cleanPlayerAction(state), PATTERNS.nothingToOpen);
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
