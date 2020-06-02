import activate from "../activate-player";
import { buildPlayer, displayMenu, optionExit } from "./tools";
import { getObjects } from "../player/inventory";
import createMenuInventoryObject from "./create-menu-inventory-object";

function buildEndingOptions(backActivate) {
  return [{ desc: "retour", todo: backActivate }];
}
const INVENTAIRE_HEADER = ["INVENTAIRE", "----------"];

function buildOptionsObjects(objects, cally) {
  return objects.reduce(function (a, o) {
    const { desc } = o;
    return [
      ...a,
      { desc, todo: createMenuInventoryObject(o, cally), optionExit },
    ];
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
