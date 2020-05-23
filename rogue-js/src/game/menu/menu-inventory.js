import activate from "../activate-player";
import { buildPlayer, display } from "./tools";
import { getObjects } from "../player/inventory";

function buildEndingOptions(backActivate) {
  return [
    { desc: "retour", todo: backActivate },
    {
      desc: "exit",
      todo: function (state) {
        const { player } = state;
        return { ...state, player: { ...player, action: null }, activate };
      },
    },
  ];
}
const INVENTAIRE_HEADER = ["INVENTAIRE", "----------"];

function buildOptionsObjects(objects) {
  return objects.reduce(function (a, o) {
    const { todo, desc } = o;
    return [...a, { desc, todo: null }];
  }, []);
}

export function createActivateInventoryMenu(activateRoot) {
  const endingOptions = buildEndingOptions(activateRoot);
  return function activateInventoryMenu(state, event) {
    const { player } = state;
    const { inventory } = player;
    const objects = getObjects(inventory);
    const optionsObject = buildOptionsObjects(objects);
    return {
      ...state,
      player: buildPlayer({
        player,
        options: [...optionsObject, ...endingOptions],
        header: INVENTAIRE_HEADER,
      }),
      activate: display,
    };
  };
}

export default createActivateInventoryMenu(activate);
