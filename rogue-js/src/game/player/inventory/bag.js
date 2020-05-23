function createInventory(max = 10) {
  return {
    objects: [],
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
  let find = false;
  const next = objects.reduce(function (a, o) {
    if (o.id === object.id && !find) return [...a, o];
  }, []);

  return { ...bag, objects: next };
}

export function getObjects({ objects } = { objects: null }) {
  return objects;
}

export default createInventory;
