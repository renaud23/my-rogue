import removeFromDungeon from "./remove-object-dungeon";
import removeFromInventory from "./remove-object-inventory";

/**
 * Retire un objet du state : de l'inventaire ou du donjon.
 * @param {*} state
 * @param {*} object
 */
function remove(state, object) {
  const { objects, player } = state;
  const { inventory } = player;
  const { level } = object;

  if (level !== undefined) {
    return { ...state, objects: removeFromDungeon(objects, object, level) };
  }
  return {
    ...state,
    player: { ...player, inventory: removeFromInventory(inventory, object) },
  };
}

export default remove;
