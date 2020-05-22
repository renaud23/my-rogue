import { PAD_BUTTON, PLAYER_ACTIONS, getTile } from "../commons";
import activateGame from "./activate-game";
import { navigateOptions } from "./commons";
import activate from "./activate-player";

function getOptions(state) {
  const { player, dungeon } = state;
  const { position } = player;
  const { currentLevel } = player;
  const data = dungeon.getData(currentLevel);
  const tile = getTile(data[position]);
  let options = [];
  if (tile.todo) {
    options = [
      ...tile.todo.map(({ desc, todo }, i) => ({
        desc: `${i + 1}. ${desc}`,
        todo,
      })),
    ];
  }

  return options;
}

function todoExit(state) {
  const { player, ...rest } = state;
  return { ...rest, player: { ...player, action: null, activate } };
}

function actionTodo(state) {
  const { player } = state;
  const options = getOptions(state);
  return {
    ...state,
    player: {
      ...player,
      action: {
        type: PLAYER_ACTIONS.action,
        header: ["ACTIONS", "-------"],
        footer: ["", "Validez avec le bouton A."],
        options: [
          ...options,
          { desc: `${options.length + 1}. exit`, todo: todoExit },
        ],
        active: 0,
      },
    },
  };
}

function navigateMenu(state, event) {
  const {
    payload: { button },
  } = event;
  const { player } = state;
  const { action } = player;
  const { active, options } = action;
  const next = navigateOptions(button, state);

  switch (button) {
    case PAD_BUTTON.up:
    case PAD_BUTTON.down:
    case PAD_BUTTON.left:
    case PAD_BUTTON.right:
      return { ...next, activate: navigateMenu };
    case PAD_BUTTON.buttonA:
      return activateGame({ ...options[active].todo(state), activate });
    default:
      return { ...next, activate: navigateMenu };
  }
}

function activateAction(state, event) {
  return { ...actionTodo(state), activate: navigateMenu };
}

export default activateAction;
