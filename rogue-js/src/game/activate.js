import { movePlayer } from "../player";
import { PAD_BUTTON, DIRECTION } from "../commons";
import activateHelp from "./activate-help";
import * as EVENTS from "./events";

function witchDirection(button) {
  switch (button) {
    case PAD_BUTTON.up:
      return DIRECTION.NORTH;
    case PAD_BUTTON.down:
      return DIRECTION.SOUTH;
    case PAD_BUTTON.left:
      return DIRECTION.WEST;
    case PAD_BUTTON.right:
      return DIRECTION.EAST;
    default:
      return "";
  }
}

function activateMove(state, action) {
  const { player, ...rest } = state;
  const np = movePlayer(witchDirection(action.payload.button), state);
  return { activate, player: np, ...rest };
}

function activate(state, action) {
  const { type, payload } = action;
  if (type === EVENTS.PAD_EVENT) {
    const { button } = payload;
    switch (button) {
      case PAD_BUTTON.buttonX:
        return activateHelp(state, action);
      case PAD_BUTTON.up:
      case PAD_BUTTON.down:
      case PAD_BUTTON.left:
      case PAD_BUTTON.right:
        return activateMove(state, action);
      default:
        return { activate, ...state };
    }
  }

  return { activate, ...state };
}

export default activate;
