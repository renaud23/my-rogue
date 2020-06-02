import isEmptyPosition from "./is-empty-position";

function getNeighbors(state) {
  const { player, dungeon } = state;
  const { position } = player;
  const { currentLevel } = player;
  const width = dungeon.getWidth(currentLevel);

  return function (pos) {
    const neighbors = [];
    if (
      isEmptyPosition(state, currentLevel, pos - width) &&
      pos - width !== position
    ) {
      neighbors.push(pos - width);
    }
    if (
      isEmptyPosition(state, currentLevel, pos + width) &&
      pos + width !== position
    ) {
      neighbors.push(pos + width);
    }
    if (isEmptyPosition(state, currentLevel, pos - 1) && pos - 1 !== position) {
      neighbors.push(pos - 1);
    }
    if (isEmptyPosition(state, currentLevel, pos + 1) && pos + 1 !== position) {
      neighbors.push(pos + 1);
    }
    return neighbors;
  };
}

export default getNeighbors;
