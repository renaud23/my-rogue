import activate from "../activate-player";
import { buildPlayer, displayMenu, optionExit } from "./tools";
import { getObjects, removeObject } from "../player/inventory";

function buildEndingOptions(backActivate) {
  return [{ desc: "retour", todo: backActivate }, optionExit];
}
const INVENTAIRE_HEADER = ["INVENTAIRE", "----------"];

function underligne(word) {
  return new Array(word.length).fill("-").join("");
}

function createThrowObject(object) {
  return function throwObject(state) {
    const { player, dungeon } = state;
    const { inventory, currentLevel, position } = player;

    // side effect
    dungeon.putObject(currentLevel, { ...object, position });

    return {
      ...state,
      player: {
        ...player,
        inventory: removeObject(inventory, object),
        action: null,
      },
    };
  };
}

function createObjectMenu(object, cally) {
  return function (state, event) {
    const { player } = state;
    return {
      ...state,
      player: buildPlayer({
        player,
        options: [
          ...object.todo,
          { desc: "jeter", todo: createThrowObject(object) },
          { desc: "retour", todo: cally },
          optionExit,
        ],
        header: [object.desc, underligne(object.desc)],
      }),
      activate: displayMenu,
    };
  };
}

function buildOptionsObjects(objects, cally) {
  return objects.reduce(function (a, o) {
    const { todo, desc } = o;
    return [...a, { desc, todo: createObjectMenu(o, cally) }];
  }, []);
}

export function createActivateInventoryMenu(activateRoot) {
  const endingOptions = buildEndingOptions(activateRoot);
  return function activateInventoryMenu(state, event) {
    const { player } = state;
    const { inventory } = player;
    const objects = getObjects(inventory);
    const optionsObject = buildOptionsObjects(objects, activateInventoryMenu);
    return {
      ...state,
      player: buildPlayer({
        player,
        options: [...optionsObject, ...endingOptions],
        header: INVENTAIRE_HEADER,
      }),
      activate: displayMenu,
    };
  };
}

export default createActivateInventoryMenu(activate);
