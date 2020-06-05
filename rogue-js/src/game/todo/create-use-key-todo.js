import {
  consumeMove,
  navigateMap,
  fillMessage,
  getObjectsAt,
} from "../commons";
import { optionExit, displayMenu, addOptionsNumbers } from "../menu/tools";
import { TYPE_OBJECT } from "../objects";
import { PAD_BUTTON, PLAYER_ACTIONS } from "../../commons";
import PATTERNS from "../message-patterns";
import { removeObjectDungeon } from "../objects";
import { removeObject } from "../player/inventory";
import activate from "../activate-player";

export function createUseKeyOnObject(key, object) {
  return function (state) {
    const { player, messages, objects } = state;
    const { currentLevel, inventory } = player;
    const { target } = key;
    const { type, kind } = object;

    if (type !== TYPE_OBJECT.chest) {
      return {
        ...state,
        messages: [...messages, PATTERNS.nothingAppended],
        player: { ...player, action: undefined },
      };
    }
    if (target !== kind) {
      return {
        ...state,
        messages: [...messages, PATTERNS.itsNotAGoodChest],
        player: { ...player, action: undefined },
      };
    }

    const newObjects = removeObjectDungeon(objects, object, currentLevel);
    const newInventory = removeObject(inventory, key);

    return {
      ...state,
      objects: newObjects,

      messages: [
        ...messages,
        fillMessage(PATTERNS.chestOpened, { chest: object }),
      ],
      player: consumeMove({
        ...player,
        inventory: newInventory,
        action: undefined,
      }),
    };
  };
}

function getOptions(state, key, position) {
  const { player } = state;
  const { currentLevel } = player;
  const objects = getObjectsAt(state, currentLevel, position);
  const options = objects.map(function (o) {
    const { desc } = o;
    return { desc: `${desc}`, todo: createUseKeyOnObject(key, o) };
  });

  return [...options];
}

function createMenu(state) {
  const { player, messages } = state;
  const { action } = player;
  const { position, key } = action;
  const options = getOptions(state, key, position);
  if (options.length === 0) {
    return {
      ...state,
      player: { ...player, action: undefined },
      messages: [...messages, PATTERNS.nothingAppended],
      activate,
    };
  }
  return {
    ...state,
    player: {
      ...player,
      action: {
        type: PLAYER_ACTIONS.menu,
        header: [`Vous voulez utiliser ${key.desc} sur,`],
        options: addOptionsNumbers([...options, optionExit]),
        footer: [" ", "Utiliser avec le bouton A.", "Sortir avec le bouton B."],
        active: 0,
      },
    },
    activate: displayMenu,
  };
}

function initializePlayer(state, key) {
  const { player } = state;
  const { position } = player;
  const { desc } = key;
  return {
    ...player,
    action: {
      type: PLAYER_ACTIONS.navigate,
      header: [`UTILISER ${desc}`, " "],
      footer: [" ", "Utiliser avec le bouton A", "Sortir avec le bouton B."],
      position,
      color: "blue",
      active: -1,
      key,
    },
  };
}

function activateNavigate(state, event) {
  const { player } = state;
  const {
    payload: { button },
  } = event;
  const next = navigateMap(state, event, 1);
  switch (button) {
    case PAD_BUTTON.buttonB:
      return { ...state, player: { ...player, action: undefined }, activate };
    case PAD_BUTTON.buttonA:
      return createMenu(next);
    case PAD_BUTTON.up:
    case PAD_BUTTON.down:
    case PAD_BUTTON.left:
    case PAD_BUTTON.right:
    default:
      return {
        ...next,
        activate: activateNavigate,
      };
  }
}

function createTodo(object) {
  return function todo(state, event = { payload: {} }) {
    return {
      ...state,
      player: initializePlayer(state, object),
      activate: activateNavigate,
    };
  };
}

export default createTodo;
