import createDisplayMenu from "./display-menu";
import { PAD_BUTTON } from "../../../commons";
import { buildPlayer } from "./tools";
import optionExit from "./options-exit";

function createActivateMenu({
  options = [optionExit],
  active = 0,
  header = ["MENU", "----"],
  footer = [" ", "Validez avec le bouton A.", "Sortir avec le bouton B."],
  activateButton = PAD_BUTTON.buttonA,
  exitButton = PAD_BUTTON.buttonB,
}) {
  const display = createDisplayMenu(activateButton, exitButton);

  return function (state) {
    const { player } = state;
    return {
      ...state,
      player: buildPlayer({ player, options, header, footer, active }),
      activate: display,
    };
  };
}

export default createActivateMenu;
