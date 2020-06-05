import { buildPlayer, optionExit, displayMenu } from "./tools";
import { finishTurn } from "../commons";
import { createActivateInventoryMenu } from "./menu-inventory";
import activateHelp from "../activate-help";
import activateAction from "../activate-action";
import activateStackMove from "./stack-move-menu";
import { PLAYER_ACTIONS } from "../../commons";
import playerMenu from "./menu-player";

function finishTurnTodo(state) {
  const { player } = state;

  return { ...state, player: finishTurn(player) };
}

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
  { desc: "Action", todo: actionTodo },
  { desc: "Aller à", todo: activateStackMove },
  { desc: "Observer", todo: lookAtTodo },
  { desc: "Joueur", todo: playerMenu },
  { desc: "Finir le tour", todo: finishTurnTodo },
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
