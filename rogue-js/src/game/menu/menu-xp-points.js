import { optionExit, displayMenu } from "./tools";
import { PLAYER_ACTIONS } from "../../commons";
import PATTERNS from "../message-patterns";

function consumePoint(state, w) {
  const { player, messages } = state;
  const { stats } = player;
  const { xpPoint } = stats;
  return {
    ...state,
    messages: [...messages, PATTERNS.consumeXpPoint],
    player: {
      ...player,
      stats: { ...stats, xpPoint: xpPoint - 1, [w]: stats[w] + 1 },
    },
  };
}

function makeTodo(witch) {
  return function (state) {
    switch (witch) {
      case "S":
        return menuXp(consumePoint(state, "strength"));
      case "A":
        return menuXp(consumePoint(state, "agility"));
      case "L":
        return menuXp(consumePoint(state, "luck"));
      case "E":
        return menuXp(consumePoint(state, "endurance"));
      default:
        return menuXp(state);
    }
  };
}

function getOptions(player) {
  const { stats } = player;
  const { xpPoint } = stats;
  if (xpPoint) {
    return [
      { desc: "Augmenter voter force (S).", todo: makeTodo("S") },
      { desc: "Augmenter votre agilité (A).", todo: makeTodo("A") },
      { desc: "Augmenter votre chance (L).", todo: makeTodo("L") },
      { desc: "Augmenter votre endurance (E).", todo: makeTodo("E") },
      optionExit,
    ];
  }
  return [optionExit];
}

function getFooter(player) {
  const { stats } = player;
  const { xpPoint } = stats;
  if (xpPoint) {
    return [" ", "choisir avec le bouton A", "Sortir avec le bouton B"];
  }
  return [
    " ",
    "Vous n'avez pas de point d'expérience.",
    "Sortir avec le bouton B",
  ];
}

function getHeader(player) {
  const { stats } = player;
  const { xpPoint } = stats;
  if (xpPoint) {
    return [
      "MENU XP",
      "-----------",
      `Vous avez ${xpPoint} points d'expériences.`,
      " ",
    ];
  }
  return ["MENU XP", "-----------"];
}

function initializePlayer(player) {
  return {
    ...player,
    action: {
      type: PLAYER_ACTIONS.menu,
      header: getHeader(player),
      options: getOptions(player),
      footer: getFooter(player),
      active: 0,
    },
  };
}

function menuXp(state, event) {
  const { player } = state;
  return {
    ...state,
    player: initializePlayer(player),
    activate: displayMenu,
  };
}

export default menuXp;
