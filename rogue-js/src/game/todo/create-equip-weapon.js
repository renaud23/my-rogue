import { removeObject, putObject } from "../player/inventory";
import { putObjectDungeon } from "../objects";

function checkInventory(inventory, currentWeapon, nextWeapon) {
  if (currentWeapon) {
    return putObject(removeObject(inventory, nextWeapon), currentWeapon);
  }
  return removeObject(inventory, nextWeapon);
}

function createEquipWeapon(weapon) {
  return function (state) {
    const { player } = state;
    const { inventory, weapon: current } = player;

    return {
      ...state,
      player: {
        ...player,
        weapon,
        inventory: checkInventory(inventory, current, weapon),
        action: null,
      },
    };
  };
}

export default createEquipWeapon;
