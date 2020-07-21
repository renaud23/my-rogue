import { putObjects } from "../../objects/dungeon-objects";

function createInventory(maxSize = 10, ...objects) {
  return {
    keys: [],
    objects: objects.reduce(function (mapObjects, o) {
      const { id } = o;
      return { ...mapObjects, [id]: o };
    }, {}),
    maxSize,
  };
}

export function putKeys(inventory, ...newKeys) {
  const { keys } = inventory;
  return { ...inventory, keys: newKeys.concat(keys) };
}

export function removeKeys(inventory, ...newKeys) {
  const { keys } = inventory;
  const nextKeys = newKeys.reduce(function (a, k) {
    return a.filter(function ({ id }) {
      return id !== k.id;
    });
  }, keys);
  return { ...inventory, keys: nextKeys };
}

export function putObject(inventory, object) {
  const { objects } = inventory;
  const { id } = object;
  return { ...inventory, objects: { ...objects, [id]: object } };
}

export function getKeys(inventory) {
  const { keys } = inventory;
  return [...keys];
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

export function filterInventory(inventory, predicate) {
  const { objects } = inventory;
  return Object.values(objects).reduce(function (a, o) {
    if (predicate(o)) {
      return [...a, o];
    }
    return a;
  }, []);
}

export function replaceObject(inventory, witch, by) {
  return putObject(removeObject(inventory, witch), by);
}

export default createInventory;
