function removeObjectFromLevel(level, object) {
  const { id } = object;
  return level.reduce(function (next, curr) {
    const { id: currId } = curr;
    if (currId === id) {
      return next;
    }
    return [...next, curr];
  }, []);
}

function remove(objects, object, index) {
  if (index < objects.length && object) {
    return objects.map(function (level, i) {
      if (index === i) {
        return removeObjectFromLevel(level, object);
      }
      return level;
    });
  }

  return objects;
}

export default remove;
