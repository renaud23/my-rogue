export function getEnnemiesAt(state, level, position) {
  const { ennemies } = state;
  return ennemies[level].filter(({ position: p }) => p === position);
}
