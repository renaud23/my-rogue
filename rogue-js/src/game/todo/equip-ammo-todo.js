import {
  removeObject,
  putObject,
  hasEnoughSpaceFor,
} from "../player/inventory";
import { consumeMove } from "../commons";

function checkInventory(inventory, ammo, nextAmmo) {
  if (ammo) {
    if (ammo.id === ammo.id) {
      // TODO msg déjà équiper
    }
    if (hasEnoughSpaceFor(inventory, Math.max(0, ammo.size - nextAmmo.size))) {
      return [putObject(removeObject(inventory, nextAmmo), ammo), true];
    }
    // todo msg pas possible de ranger currentWeapon
    return [inventory, false];
  }
  return [removeObject(inventory, nextAmmo), true];
}

function equipWeaponTodo(state) {
  const { player } = state;
  const { inventory, ammo: current, action } = player;
  const { object: nextAmmo } = action;
  const [nextInventory, done] = checkInventory(inventory, current, nextAmmo);
  const nextPlayer = {
    ...player,
    ammo: nextAmmo,
    inventory: nextInventory,
    action: undefined,
  };
  return {
    ...state,
    player: done ? consumeMove(nextPlayer) : { ...player, action: undefined },
  };
}

export default equipWeaponTodo;
