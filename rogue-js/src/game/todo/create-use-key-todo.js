import { consumeMove, navigateMap, fillMessage } from "../commons";
import { TYPE_OBJECT } from "../objects";
import { PAD_BUTTON, PLAYER_ACTIONS } from "../../commons";
import PATTERNS from "../message-patterns";
import { removeObjectDungeon } from "../objects";
import { removeObject } from "../player/inventory";
import activate from "../activate-player";
import { buildPlayer } from "../menu/tools";

function utiliserKey(state, key, position) {
  const { player, objects, messages } = state;
  const { currentLevel, inventory } = player;
  const { target } = key;
  const chest = objects[currentLevel].find(function (o) {
    const { type, position: chestPos } = o;
    return type === TYPE_OBJECT.chest && position === chestPos;
  });

  if (!chest) {
    const message = fillMessage(PATTERNS.nothingToOpen, key);
    return {
      ...state,
      messages: [...messages, message],
      player: { ...player, action: null },
    };
  }
  if (chest.id !== target) {
    const message = PATTERNS.badKey;
    return {
      ...state,
      messages: [...messages, message],
      player: { ...player, action: null },
    };
  }

  const newObjects = removeObjectDungeon(objects, chest, currentLevel);
  const newInventory = removeObject(inventory, key);

  return {
    ...state,
    objects: newObjects,
    messages: [...messages, fillMessage(PATTERNS.chestOpened, { chest })],
    player: consumeMove({ ...player, action: null, inventory: newInventory }),
    activate,
  };
}

function activateNavigate(state, event) {
  const { player } = state;
  const { action } = player;
  const { object, position } = action;
  const {
    payload: { button },
  } = event;
  const next = navigateMap(state, event, 1);
  switch (button) {
    case PAD_BUTTON.buttonB:
      return { ...state, player: { ...player, action: null } };
    case PAD_BUTTON.buttonA:
      return utiliserKey(next, object, position);
    case PAD_BUTTON.up:
    case PAD_BUTTON.down:
    case PAD_BUTTON.left:
    case PAD_BUTTON.right:
    default:
      return { ...next, activate: activateNavigate };
  }
}

function createTodo(object) {
  const { desc } = object;
  return function todo(state) {
    const { player } = state;
    const { position } = player;

    return {
      ...state,
      player: buildPlayer({
        player,
        type: PLAYER_ACTIONS.navigate,
        header: ["ACTIONS", "-------"],
        footer: [
          " ",
          `Utiliser ${desc} avec le bouton A.`,
          "Sortir avec le bouton B.",
        ],
        position,
        color: "blue",
        active: -1,
        object,
      }),
      activate: activateNavigate,
    };
  };
}

export default createTodo;
