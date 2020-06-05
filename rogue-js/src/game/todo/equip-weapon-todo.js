import {
  removeObject,
  putObject,
  hasEnoughSpaceFor,
} from "../player/inventory";
import { consumeMove } from "../commons";

function checkInventory(inventory, currentWeapon, nextWeapon) {
  if (currentWeapon) {
    if (currentWeapon.id === nextWeapon) {
      // TODO msg déjà équiper
    }
    if (hasEnoughSpaceFor(inventory, currentWeapon.size)) {
      return [
        putObject(removeObject(inventory, nextWeapon), currentWeapon),
        true,
      ];
    }
    // todo msg pas possible de ranger currentWeapon
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
