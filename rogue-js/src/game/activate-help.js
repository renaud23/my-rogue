import { PAD_BUTTON, PLAYER_ACTIONS } from "../commons";
import { navigateMap } from "./commons";
import activate from "./activate-player";

export function buildActionHelp(position) {
  return { type: PLAYER_ACTIONS.help, position };
}

function activateHelp(state, event) {
  const { player, ...rest } = state;
  const {
    payload: { button },
  } = event;
  if (!player.action) {
    return {
      activate: activateHelp,
      player: { ...player, action: buildActionHelp(player.position) },
      ...rest,
    };
  }

  const next = navigateMap({ ...state, activate: activateHelp }, event);
  switch (button) {
    case PAD_BUTTON.up:
    case PAD_BUTTON.down:
    case PAD_BUTTON.left:
    case PAD_BUTTON.right:
      return { ...next, activate: activateHelp };
    default:
      return { activate, player: { ...player, action: null }, ...rest };
  }
}

export default activateHelp;
