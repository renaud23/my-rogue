function getObjectsAt(state, level, position) {
  const { objects } = state;
  return objects[level].filter(({ position: p }) => p === position);
}

export default getObjectsAt;
