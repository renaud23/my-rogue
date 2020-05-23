import { PAD_BUTTON, PLAYER_ACTIONS } from "../../../commons";
import activate from "../../activate-player";
import { navigateOptions } from "../../commons";

export function createDisplayMenu(
  activateButton = PAD_BUTTON.buttonY,
  exitButton = PAD_BUTTON.buttonB
) {
  return function display(state, event) {
    const {
      payload: { button },
    } = event;
    const { player } = state;
    const { action } = player;
    const { active, options } = action;

    switch (button) {
      case PAD_BUTTON.up:
      case PAD_BUTTON.down:
      case PAD_BUTTON.left:
      case PAD_BUTTON.right: {
        const next = navigateOptions(button, state);
        return { ...next, activate: display };
      }
      case exitButton:
        return { ...state, player: { ...player, action: null }, activate };
      case activateButton: {
        const next = options[active].todo(state);
        const { activate: cally } = next;
        return { ...next, activate: cally || activate };
      }
      default:
        return { ...state, activate: display };
    }
  };
}

export default createDisplayMenu();
