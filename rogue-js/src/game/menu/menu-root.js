import { buildPlayer, optionExit } from "./tools";
import { createActivateInventoryMenu } from "./menu-inventory";
import { displayMenu } from "./tools";

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
    activate: displayMenu,
  };
}

export default activateRootMenu;
