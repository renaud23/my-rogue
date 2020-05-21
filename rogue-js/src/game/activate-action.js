import { PAD_BUTTON, PLAYER_ACTIONS } from "../commons";
import activate from "./activate-player";

function activateAction(state, action) {
  return { activate, ...state };
}

export default activateAction;
