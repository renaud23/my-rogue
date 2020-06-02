import { optionExit, displayMenu, addOptionsNumbers } from "../menu/tools";
import { PLAYER_ACTIONS } from "../../commons";
import { createUseKeyOnObject } from "./create-use-key-todo";
import { TYPE_OBJECT } from "../objects";

function getOptions(state, chest) {
  const { player } = state;
  const { inventory } = player;
  const { objects } = inventory;
  return objects.reduce(function (a, o) {
    const { type, desc } = o;
    if (type === TYPE_OBJECT.key) {
      return [...a, { desc, todo: createUseKeyOnObject(o, chest) }];
    }
    return a;
  }, []);
}

function initPlayer(state, chest) {
  const { player } = state;
  const options = getOptions(state, chest);
  const { desc } = chest;
  return {
    ...player,
    action: {
      type: PLAYER_ACTIONS.menu,
      header: [`vous voulez ouvrir ${desc} avec,`],
      options: addOptionsNumbers([...options, optionExit]),
      footer: [" ", "Utiliser avec le bouton A.", "Sortir avec le bouton B."],
      active: 0,
      chest,
    },
  };
}

function create(chest) {
  return function (state) {
    return {
      ...state,
      player: initPlayer(state, chest),
      activate: displayMenu,
    };
  };
}

export default create;
