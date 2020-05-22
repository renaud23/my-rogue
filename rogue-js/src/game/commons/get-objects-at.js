function getObjectsAt(state, position) {
  const { player, dungeon } = state;
  const { currentLevel } = player;
  return dungeon
    .getObjects(currentLevel)
    .filter(({ position: p }) => p === position);
}

export default getObjectsAt;
