import { PAD_BUTTON, PLAYER_ACTIONS, maxMin } from "../commons";
import { navigateOptions } from "./commons";
import activateHelp from "./activate-help";
import activateAction from "./activate-action";
import activate from "./activate-player";

function exit(state) {
  const { player } = state;
  return { ...state, player: { ...player, action: null }, activate };
}

function buildPlayer(player, options, active, header = ["MENU", "----"]) {
  return {
    ...player,
    action: { type: PLAYER_ACTIONS.menu, options, active, header },
  };
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

function actionTodo(state) {
  const { player } = state;
  return {
    ...state,
    player: { ...player, action: null },
    activate: activateAction,
  };
}

function rootTodo(state) {
  const { player } = state;
  return {
    ...state,
    player: buildPlayer(player, ROOT_MENU, 0),
    activate: display,
  };
}

const INVENTAIRE_MENU = [{ desc: "1. retour", todo: rootTodo }];

const ROOT_MENU = [
  {
    desc: "1. inventaire",
    todo: ({ player, ...state }) => ({
      ...state,
      player: buildPlayer(player, INVENTAIRE_MENU, 0, [
        "INVENTAIRE",
        "----------",
      ]),
      activate: display,
    }),
  },
  { desc: "2. action", todo: actionTodo },
  { desc: "3. observer", todo: lookAtTodo },
  { desc: "4. exit", todo: exit },
];

function display(state, event) {
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

function root(state, event) {
  return rootTodo(state);
}

export default root;