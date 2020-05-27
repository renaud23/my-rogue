import { PAD_BUTTON, PLAYER_ACTIONS, getTile } from "../commons";
import { getObjectsAt } from "./commons";
import { buildPlayer } from "./menu/tools";
import activate from "./activate-player";
import { createTakeObjectTodo } from "./todo";
import { createDisplayMenu } from "./menu";

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
    return [
      ...a,
      ...o.todo,
      { desc: `prendre ${o.desc}`, todo: createTakeObjectTodo(o) },
    ];
  }, []);

  return [...options];
}

function getOptions(state) {
  const { player, dungeon } = state;
  const { position } = player;
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
  const options = [...getOptions(state), { desc: `exit`, todo: todoExit }];

  return {
    ...state,
    player: buildPlayer({ player, header: ["ACTIONS", "-------"], options }),
  };
}

function activateAction(state, event) {
  return {
    ...actionTodo(state),
    activate: createDisplayMenu(PAD_BUTTON.buttonA, PAD_BUTTON.buttonB),
  };
}

export default activateAction;
