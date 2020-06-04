import { PAD_BUTTON } from "../commons";
import activate from "./activate-player";
import { isTurnFinish } from "./commons";
import { buildPlayer } from "./menu/tools";

export function isNeedWait(
  state,
  messages = [
    "Vous ne pouvez plus rien faire sur ce tour.",
    "Appuyer sur le bouton A.",
  ]
) {
  const { player } = state;
  if (isTurnFinish(player)) {
    return {
      ...state,
      activate: activateWait,
      player: buildPlayer({
        player,
        options: [],
        header: [...messages],
        footer: [],
      }),
    };
  }
  return state;
}

function activateWait(state, event = { payload: {} }) {
  const { player } = state;
  const { payload } = event;
  const { button } = payload;
  switch (button) {
    case PAD_BUTTON.up:
    case PAD_BUTTON.down:
    case PAD_BUTTON.left:
    case PAD_BUTTON.right:
    case PAD_BUTTON.buttonA:
      return activate({
        ...state,
        activate,
        player: { ...player, action: null },
      });
    default:
      return {
        ...state,
        activate: activateWait,
      };
  }
}

export default activateWait;
