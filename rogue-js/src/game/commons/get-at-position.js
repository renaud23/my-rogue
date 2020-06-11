export function getObjectsAt(state, level, position) {
  const { objects } = state;
  if (level >= objects.length) {
    return [];
  }

  return objects[level].filter(({ position: p }) => p === position);
}

export function getEnnemiesAt(state, level, position) {
  const { ennemies } = state;
  return ennemies[level].filter(({ position: p }) => p === position);
}
