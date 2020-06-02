import { buildPlayer, optionExit, displayMenu } from "./tools";
import { createActivateInventoryMenu } from "./menu-inventory";
import activateHelp from "../activate-help";
import activateAction from "../activate-action";
import { PLAYER_ACTIONS } from "../../commons";

function lookAtTodo(state) {
  const { player } = state;
  const { position } = player;
  return {
    ...state,
    player: { ...player, action: { type: PLAYER_ACTIONS.help, position } },
    activate: activateHelp,
  };
}

/** */
function actionTodo(state) {
  return {
    ...activateAction(state),
  };
}

const ROOT_MENU_OPTIONS = [
  {
    desc: "inventaire",
    todo: createActivateInventoryMenu(activateRootMenu),
  },
  { desc: "action", todo: actionTodo },
  { desc: "observer", todo: lookAtTodo },
];

function activateRootMenu(state, event) {
  const { player } = state;
  return {
    ...state,
    player: buildPlayer({
      player,
      options: [...ROOT_MENU_OPTIONS, optionExit],
    }),
    activate: displayMenu,
  };
}

export default activateRootMenu;
