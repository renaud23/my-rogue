import { putObjects } from "../../objects/dungeon-objects";

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
  const { id } = object;
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

export function isInInventory(inventory, object) {
  if (object) {
    const { objects } = inventory;
    const { id } = object;
    return id in objects;
  }
  return false;
}

export function lookAtInventory(inventory, predicate) {
  const { objects } = inventory;
  return Object.values(objects).find(predicate);
}

export function replaceObject(inventory, witch, by) {
  return putObject(removeObject(inventory, witch), by);
}

export default createInventory;
