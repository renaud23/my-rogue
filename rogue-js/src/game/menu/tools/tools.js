import { PAD_BUTTON, PLAYER_ACTIONS } from "../../../commons";
import { navigateOptions } from "../../commons";

export function addOptionsNumbers(options) {
  return options.map(function (o, i) {
    const { desc } = o;
    return { ...o, desc: `${i + 1}. ${desc}` };
  });
}

export function display(state, event) {
  const {
    payload: { button },
  } = event;
  const { player } = state;
  const { action } = player;
  const { active, options } = action;
  const next = navigateOptions(button, state);
  switch (button) {
    case PAD_BUTTON.buttonY:
      return options[active].todo(state);
    default:
      return { ...next, activate: display };
  }
}

export function buildPlayer({
  player,
  options,
  active = 0,
  header = ["MENU", "----"],
  footer = [" ", "Validez avec le bouton Y."],
}) {
  return {
    ...player,
    action: {
      type: PLAYER_ACTIONS.menu,
      options: addOptionsNumbers(options),
      active,
      header,
      footer,
    },
  };
}
