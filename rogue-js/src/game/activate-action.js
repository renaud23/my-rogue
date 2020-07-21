import { PAD_BUTTON, PLAYER_ACTIONS, getTile } from "../commons";
import { getObjectsAt } from "./objects/dungeon-objects";
import { buildPlayer } from "./menu/tools";
import activate from "./activate-player";
import { takeObjectTodo, takeKeyTodo } from "./todo";
import { TYPE_OBJECT } from "./objects";
import displayMenu from "./menu/tools/display-menu";
import { computeDesc, computeTodoDesc, navigateMap } from "./commons";

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

function getTakeableTodo(object) {
  const { type } = object;
  switch (type) {
    case TYPE_OBJECT.key:
      return takeKeyTodo;
    default:
      return takeObjectTodo;
  }
}

function appendTakeable(object, toDoes) {
  const { takeable, type } = object;
  if (takeable) {
    return [
      ...toDoes,
      {
        desc: `Prendre ${computeDesc(object)}.`,
        todo: getTakeableTodo(object),
        args: object,
      },
    ];
  }
  return toDoes;
}

function optionsObjects(objects) {
  const options = objects.reduce(function (a, o) {
    const { todo } = o;
    const toDoes = [
      ...a,
      ...todo.map(function (td) {
        return { ...td, desc: computeTodoDesc(td, o), args: o };
      }),
    ];
    return appendTakeable(o, toDoes);
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
