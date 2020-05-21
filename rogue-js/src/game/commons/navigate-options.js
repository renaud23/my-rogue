import { PAD_BUTTON, maxMin } from "../../commons";
import activate from "../activate-player";

function moveActive(player, how) {
  const { action } = player;
  const { active, options } = action;

  return {
    ...player,
    action: { ...action, active: maxMin(active + how, 0, options.length - 1) },
  };
}

function navigate(button, state, callback, precCallback = activate) {
  const { player } = state;
  const {
    action: { options, active },
  } = player;
  switch (button) {
    case PAD_BUTTON.up:
      return {
        activate: callback,
        ...state,
        player: moveActive(player, -1),
      };
    case PAD_BUTTON.down:
      return {
        activate: callback,
        ...state,
        player: moveActive(player, 1),
      };
    case PAD_BUTTON.buttonY:
      return options[active].todo({ ...state, activate: precCallback });
    default:
      return { activate: precCallback, ...state };
  }
}

export default navigate;
