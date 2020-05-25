import { movePlayer } from "./player";
import { isTurnFinish, nextTurn } from "./commons";
import { PAD_BUTTON, DIRECTION } from "../commons";
import activateHelp from "./activate-help";
import activateMenu from "./activate-menu";
import activateAction from "./activate-action";
import activateGame from "./activate-game";
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
        return activateMenu(state, event);
      case PAD_BUTTON.buttonA:
        return activateAction(state, event);
      case PAD_BUTTON.up:
      case PAD_BUTTON.down:
      case PAD_BUTTON.left:
      case PAD_BUTTON.right:
        return activateMove(state, event);
      default:
        return { activate, ...state };
    }
  }

  return { activate, ...state };
}

// export default activatePlayer;

function activate(state, event) {
  const { player, ennemies } = state;
  if (!isTurnFinish(player)) {
    return activatePlayer(state, event);
  }

  const [nextState, endTurn] = activateGame(state);
  if (endTurn) {
    const np = nextTurn(player);
    return { ...nextState, player: { ...np }, activate };
  }
  return { ...nextState, activate };
}

export default activate;

/*
function mainActivate(state, event){
  const {  player,ennemies } = state
   if(isTurnFinish(player) ){

   }
}


*/
