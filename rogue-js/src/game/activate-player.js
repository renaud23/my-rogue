import { movePlayer } from "./player";
import { isTurnFinish, nextTurn, appendMessages, fillMessage } from "./commons";
import PATTERNS from "./message-patterns";
import { PAD_BUTTON, DIRECTION, PLAYER_ACTIONS } from "../commons";
import activateHelp from "./activate-help";
import activateMenu from "./activate-menu";
import activateAction from "./activate-action";
import activateGame from "./activate-game";
import activateShoot from "./activate-shoot";
import * as EVENTS from "./events";
import { isNeedWait } from "./activate-wait";

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
  return { ...rest, activate, player: np };
}

/**
 *
 * @param {dungeon, player} state
 * @param {type, payload} action
 */
function activatePlayer(state, event) {
  const { type, payload } = event;
  if (type === EVENTS.PAD_EVENT) {
    const { button } = payload;
    switch (button) {
      case PAD_BUTTON.buttonX:
        return activateHelp(state, event);
      case PAD_BUTTON.buttonY:
        return isNeedWait(activateMenu(state, event));
      case PAD_BUTTON.buttonA:
        return isNeedWait(activateAction(state, event));
      case PAD_BUTTON.buttonB:
        return activateShoot(state, event);
      case PAD_BUTTON.up:
      case PAD_BUTTON.down:
      case PAD_BUTTON.left:
      case PAD_BUTTON.right:
        return isNeedWait(activateMove(state, event));
      default:
        return { ...state, activate };
    }
  }

  return { ...state, activate };
}

/**
 * Main loop
 * @param {*} state
 * @param {*} event
 */

function activate(state, event) {
  const { player } = state;
  if (!isTurnFinish(player)) {
    // TODO remove dead ennemies.
    return activatePlayer(state, event);
  }
  const [nextState, endTurn] = activateGame(state);
  // TODO check status player : dead ?
  if (endTurn) {
    // TODO activate other things if necessary.
    const np = nextTurn(player);
    return {
      ...appendMessages(nextState, fillMessage(PATTERNS.nextTurn, np)),
      player: { ...np },
      activate,
    };
  }
  return activate({ ...nextState, activate });
}

export default activate;
