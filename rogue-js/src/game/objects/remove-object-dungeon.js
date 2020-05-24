function doIt(objects, object, level) {
  return objects.map(function (objectsLevel, i) {
    if (i === level) {
      return objectsLevel.reduce(function (a, o) {
        if (o.id === object.id) {
          return a;
        }
        return [...a, o];
      }, []);
    }
    return objectsLevel;
  });
}

export function removeFromObjectData(objects, object) {
  const { level } = object;
  return doIt(objects, object, level);
}

function remove(objects, object, level) {
  if (level !== undefined) {
    return doIt(objects, object, level);
  }
  return removeFromObjectData(objects, object);
}

export default remove;
