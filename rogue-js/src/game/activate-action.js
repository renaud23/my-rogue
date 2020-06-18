import { PAD_BUTTON, PLAYER_ACTIONS, getTile } from "../commons";
import { getObjectsAt } from "./objects/dungeon-objects";
import { buildPlayer } from "./menu/tools";
import activate from "./activate-player";
import { takeObjectTodo } from "./todo";
import { navigateMap } from "./commons";
import displayMenu from "./menu/tools/display-menu";
import { computeDesc } from "./commons";

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
    const todos = [
      ...a,
      ...todo.map(function (t) {
        return { ...t, args: o };
      }),
    ];
    return takeable
      ? [
          ...todos,
          {
            desc: `Prendre ${computeDesc(o)}.`,
            todo: takeObjectTodo,
            args: o,
          },
        ]
      : todos;
  }, []);

  return [...options];
}

function getOptions(state, position) {
  const { player, dungeon, objects: dungeonsObjects } = state;
  const { currentLevel } = player;
  const data = dungeon.getData(currentLevel);
  const tile = getTile(data[position]);

  const objects = getObjectsAt(dungeonsObjects, currentLevel, position);
  const opt = optionsTile(tile);
  const opo = optionsObjects(objects);

  return [...opt, ...opo];
}

function todoExit(state) {
  const { player, ...rest } = state;
  return { ...rest, player: { ...player, action: undefined, activate } };
}

function actionTodo(state) {
  const { player } = state;
  const { action } = player;
  const { position } = action;
  const options = [
    ...getOptions(state, position),
    { desc: `exit`, todo: todoExit },
  ];

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

export function activateMenuAction(state, event) {
  return {
    ...actionTodo(state),
    activate: displayMenu,
  };
}

function moveIronSight(state, event) {
  const { player } = state;
  const {
    payload: { button },
  } = event;
  const next = navigateMap(state, event, 1);

  switch (button) {
    case PAD_BUTTON.buttonB:
      return { ...state, activate, player: { ...player, action: undefined } };
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
      };
  }
}

function initializePlayer(state) {
  const { player } = state;
  const { position } = player;
  return {
    ...player,
    action: { type: PLAYER_ACTIONS.navigate, position, color: "blue" },
  };
}

function activateAction(state, event) {
  return {
    ...state,
    player: initializePlayer(state),
    activate: moveIronSight,
  };
}

export default activateAction;
