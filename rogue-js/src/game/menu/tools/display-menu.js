import { PAD_BUTTON } from "../../../commons";
import activate from "../../activate-player";
import { navigateOptions } from "../../commons";
import { isNeedWait } from "../../activate-wait";

export function createDisplayMenu(
  activateButton = PAD_BUTTON.buttonA,
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
        return { ...state, player: { ...player, action: undefined }, activate };
      case activateButton: {
        const { todo, args } = options[active];
        const next = todo(state, args);
        const { activate: cally } = next;

        return isNeedWait({ ...next, activate: cally || activate });
      }
      default:
        return { ...state, activate: display };
    }
  };
}

export default createDisplayMenu();
