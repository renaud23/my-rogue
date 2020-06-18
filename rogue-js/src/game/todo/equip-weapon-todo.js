import {
  removeObject,
  putObject,
  hasEnoughSpaceFor,
  replaceObject,
} from "../player/inventory";
import { consumeMove } from "../commons";

function checkInventory(inventory, currentWeapon, nextWeapon) {
  if (currentWeapon) {
    if (hasEnoughSpaceFor(inventory, currentWeapon.size)) {
      return [replaceObject(inventory, nextWeapon, currentWeapon), true];
    }

    return [inventory, false];
  }
  return [removeObject(inventory, nextWeapon), true];
}

function equipWeaponTodo(state) {
  const { player } = state;
  const { inventory, weapon: current, action } = player;
  const { object: weapon } = action;
  const [nextInventory, done] = checkInventory(inventory, current, weapon);
  const nextPlayer = {
    ...player,
    weapon,
    inventory: nextInventory,
    action: undefined,
  };
  return {
    ...state,
    player: done ? consumeMove(nextPlayer) : { ...player, action: undefined },
  };
}

export default equipWeaponTodo;
