function createInventory(maxSize = 10, ...objects) {
  return {
    objects: objects.reduce(function (mapObjects, o) {
      const { id } = o;
      return { ...mapObjects, [id]: o };
    }, {}),
    maxSize,
  };
}

export function putObject(inventory, object) {
  const { objects } = inventory;
  const { id } = objects;
  return { ...inventory, objects: { ...objects, [id]: object } };
}

export function removeObject(inventory, object) {
  const { objects } = inventory;
  const { id } = object;
  const next = { ...objects };
  delete next[id];
  return { ...inventory, objects: next };
}

export function getObjects(inventory) {
  const { objects } = inventory;
  return Object.values(objects);
}

function computeSize(inventory) {
  return getObjects(inventory).reduce(function (how, o) {
    return how + o.size;
  }, 0);
}

export function hasEnoughSpaceFor(inventory, required) {
  const { maxSize } = inventory;
  return computeSize(inventory) + required <= maxSize;
}

export default createInventory;
