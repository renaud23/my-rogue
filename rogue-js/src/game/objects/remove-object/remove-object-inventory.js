import { removeObject } from "../../player/inventory";

function remove(inventory, object) {
  if (object) {
    return removeObject(inventory, object);
  }

  return inventory;
}

export default remove;
