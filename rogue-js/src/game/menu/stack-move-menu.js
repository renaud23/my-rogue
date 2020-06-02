import activateAutoPlay from "../activate-auto-play";
import { PAD_BUTTON, PLAYER_ACTIONS, getTile } from "../../commons";
import activate from "../activate-player";
import navigateMap, { createNavigate } from "../commons/navigate-map";
import { aStarPath } from "../ennemies/path-finding";

function initializePlayer(state) {
  const { player } = state;
  const { position } = player;
  return {
    ...player,
    action: { type: PLAYER_ACTIONS.navigate, position, color: "magenta" },
  };
}

function activateChoice(state) {
  const { player } = state;
  const { position: posPlayer, action } = player;
  const { position: targetPos } = action;
  const { path } = action;
  if (path.length) {
    return activateAutoPlay({
      ...state,
      player: { ...player, path, action: null },
    });
  }

  return { ...state, player: { ...player, action: null }, activate };
}
//path: ,

function checkPath(state) {
  const { player } = state;
  const { position, action } = player;
  const { position: target } = action;

  return {
    ...player,
    action: { ...action, path: aStarPath(state)(position, target) },
  };
}

function navigateFunction(state, event) {
  const { player } = state;
  const { position } = player;
  const {
    payload: { button },
  } = event;
  const next = navigateMap(state, event);
  const {
    player: { action },
  } = next;
  const { position: target } = action;
  switch (button) {
    case PAD_BUTTON.buttonB:
      return { ...state, activate, player: { ...player, action: null } };
    case PAD_BUTTON.buttonA:
      return activateChoice(state, event);
    case PAD_BUTTON.up:
    case PAD_BUTTON.down:
    case PAD_BUTTON.left:
    case PAD_BUTTON.right:
    default:
      return {
        ...next,
        activate: navigateFunction,
        player: checkPath(next),
      };
  }
}

function activateStackMove(state, event) {
  return {
    ...state,
    player: initializePlayer(state),
    activate: navigateFunction, //createNavigate(activateChoice),
  };
}

export default activateStackMove;
