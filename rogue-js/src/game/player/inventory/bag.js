function createInventory(max = 10, ...objects) {
  return {
    objects: [...objects],
    size: 0,
    max,
  };
}

export function putObject(bag, object) {
  const { objects, size, max } = bag;
  if (size + object.size < max) {
    return { objects: [...objects, object], size: size + object.size, max };
  }
  return bag;
}

export function removeObject(bag, object) {
  const { objects } = bag;
  const next = objects.reduce(function (a, o) {
    if (o.id === object.id) {
      return a;
    }
    return [...a, o];
  }, []);

  return { ...bag, objects: next };
}

export function getObjects({ objects } = { objects: null }) {
  return objects;
}

export function hasEnoughSpaceFor(inventory, required) {
  const { size, max } = inventory;

  return size + required <= max;
}

export default createInventory;
