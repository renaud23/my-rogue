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

function createEquipWeapon(weapon) {
  return function (state) {
    const { player } = state;
    const { inventory, weapon: current } = player;
    const [nextInventory, done] = checkInventory(inventory, current, weapon);
    const nextPlayer = {
      ...player,
      weapon,
      inventory: nextInventory,
      action: null,
    };
    return {
      ...state,
      player: done ? consumeMove(nextPlayer) : { ...player, action: null },
    };
  };
}

export default createEquipWeapon;
