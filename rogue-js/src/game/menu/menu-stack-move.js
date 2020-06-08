import activateAutoPlay from "../activate-auto-play";
import { PAD_BUTTON, PLAYER_ACTIONS } from "../../commons";
import activate from "../activate-player";
import navigateMap from "../commons/navigate-map";
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
  const { action } = player;
  const { path } = action;
  if (path.length) {
    return activateAutoPlay({
      ...state,
      player: { ...player, path, action: undefined },
    });
  }

  return { ...state, player: { ...player, action: undefined }, activate };
}
//path: ,

export function checkPath(state) {
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
  const {
    payload: { button },
  } = event;
  const next = navigateMap(state, event);
  switch (button) {
    case PAD_BUTTON.buttonB:
      return { ...state, activate, player: { ...player, action: undefined } };
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
