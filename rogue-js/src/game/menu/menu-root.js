import activate from "../activate-player";
import { buildPlayer, display, optionExit } from "./tools";
import { createActivateInventoryMenu } from "./menu-inventory";

const ROOT_MENU_OPTIONS = [
  {
    desc: "inventaire",
    todo: createActivateInventoryMenu(activateRootMenu),
  },
  //   { desc: "2. action", todo: actionTodo },
  //   { desc: "3. observer", todo: lookAtTodo },
  optionExit,
];

function activateRootMenu(state, event) {
  const { player } = state;
  return {
    ...state,
    player: buildPlayer({ player, options: ROOT_MENU_OPTIONS }),
    activate: display,
  };
}

export default activateRootMenu;
