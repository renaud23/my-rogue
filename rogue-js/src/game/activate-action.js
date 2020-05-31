import { PAD_BUTTON, PLAYER_ACTIONS, getTile } from "../commons";
import { getObjectsAt } from "./commons";
import { buildPlayer } from "./menu/tools";
import activate from "./activate-player";
import { createTakeObjectTodo } from "./todo";
import { navigateMap } from "./commons";
import displayMenu from "./menu/tools/display-menu";

function optionsDescNumber(options) {
  return options.map(function ({ desc, ...r }, i) {
    return { desc: `${i + 1}. ${desc}`, ...r };
  });
}

function optionsTile(tile) {
  if (tile.todo) {
    return [
      ...tile.todo.map(({ desc, todo }, i) => ({
        desc,
        todo,
      })),
    ];
  }
  return [];
}

function optionsObjects(objects) {
  const options = objects.reduce(function (a, o) {
    const { takeable, todo } = o;
    const todos = [...a, ...todo];
    return takeable
      ? [...todos, { desc: `prendre ${o.desc}`, todo: createTakeObjectTodo(o) }]
      : todos;
  }, []);

  return [...options];
}

function getOptions(state) {
  const { player, dungeon } = state;
  const { action } = player;
  const { position } = action;
  const { currentLevel } = player;
  const data = dungeon.getData(currentLevel);
  const tile = getTile(data[position]);

  const objects = getObjectsAt(state, currentLevel, position);
  const opt = optionsTile(tile);
  const opo = optionsObjects(objects);

  return optionsDescNumber([...opt, ...opo]);
}

function todoExit(state) {
  const { player, ...rest } = state;
  return { ...rest, player: { ...player, action: null, activate } };
}

function actionTodo(state) {
  const { player } = state;
  const { action } = player;
  const { position } = action;
  const options = [...getOptions(state), { desc: `exit`, todo: todoExit }];

  return {
    ...state,
    player: buildPlayer({
      player,
      header: ["ACTIONS", "-------"],
      options,
      position,
    }),
  };
}

function activateMenuAction(state, event) {
  return {
    ...actionTodo(state),
    activate: displayMenu,
  };
}

function getActionPosition(state) {
  const {
    player: {
      action: { position },
    },
  } = state;
  return position;
}

function moveIronSight(state, event) {
  const { player } = state;
  const {
    payload: { button },
  } = event;
  const next = navigateMap(state, event, 1);
  switch (button) {
    case PAD_BUTTON.buttonB:
      return { ...state, activate, player: { ...player, action: null } };
    case PAD_BUTTON.buttonA:
      return { ...activateMenuAction(next, event) };
    case PAD_BUTTON.up:
    case PAD_BUTTON.down:
    case PAD_BUTTON.left:
    case PAD_BUTTON.right:
    default:
      return {
        ...next,
        activate: moveIronSight,
        player: buildPlayer({
          player,
          type: PLAYER_ACTIONS.navigate,
          header: ["ACTIONS", "-------"],
          footer: [" ", "Sortir avec le bouton B."],
          options: getOptions(next),
          position: getActionPosition(next),
          color: "blue",
          active: -1,
        }),
      };
  }
}

function activateAction(state, event) {
  const { player } = state;
  const { position } = player;

  const action = { type: PLAYER_ACTIONS.navigate, position, color: "blue" };
  const np = { ...player, action };
  const ns = { ...state, player: np };

  return {
    ...state,
    player: buildPlayer({
      player,
      type: PLAYER_ACTIONS.navigate,
      header: ["ACTIONS", "-------"],
      footer: [
        " ",
        "Entrer dans le menu avec le bouton A.",
        "Sortir avec le bouton B.",
      ],
      options: getOptions(ns),
      position: getActionPosition(ns),
      color: "blue",
      active: -1,
    }),
    activate: moveIronSight,
  };
}

export default activateAction;
