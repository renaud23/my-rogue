import activate from "../activate-player";
import { buildPlayer, displayMenu, optionExit } from "./tools";
import { getObjects, removeObject } from "../player/inventory";
import createMenuInventoryObject from "./create-menu-inventory-object";
import { putObjectDungeon } from "../objects";

function buildEndingOptions(backActivate) {
  return [{ desc: "retour", todo: backActivate }, optionExit];
}
const INVENTAIRE_HEADER = ["INVENTAIRE", "----------"];

// function underligne(word) {
//   return new Array(word.length).fill("-").join("");
// }

// function createThrowObject(object) {
//   return function throwObject(state) {
//     const { player, objects } = state;
//     const { inventory, currentLevel, position } = player;

//     const newObjects = putObjectDungeon(
//       objects,
//       { ...object, position },
//       currentLevel
//     );

//     return {
//       ...state,
//       objects: newObjects,
//       player: {
//         ...player,
//         inventory: removeObject(inventory, object),
//         action: null,
//       },
//     };
//   };
// }

// function createObjectMenu(object, cally) {
//   return function (state, event) {
//     const { player } = state;
//     return {
//       ...state,
//       player: buildPlayer({
//         player,
//         options: [
//           ...object.todo,
//           { desc: "jeter", todo: createThrowObject(object) },
//           { desc: "retour", todo: cally },
//           optionExit,
//         ],
//         header: [object.desc, underligne(object.desc)],
//       }),
//       activate: displayMenu,
//     };
//   };
// }

function buildOptionsObjects(objects, cally) {
  return objects.reduce(function (a, o) {
    const { desc } = o;
    return [...a, { desc, todo: createMenuInventoryObject(o, cally) }];
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
