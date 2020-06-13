import isEmptyPosition from "./is-empty-position";

function getNeighbors(state, isEmpty = isEmptyPosition) {
  const { player, dungeon } = state;
  const { currentLevel } = player;
  const width = dungeon.getWidth(currentLevel);

  return function (pos) {
    const neighbors = [];
    if (isEmpty(state, currentLevel, pos - width)) {
      neighbors.push(pos - width);
    }
    if (isEmpty(state, currentLevel, pos + width)) {
      neighbors.push(pos + width);
    }
    if (isEmpty(state, currentLevel, pos - 1)) {
      neighbors.push(pos - 1);
    }
    if (isEmpty(state, currentLevel, pos + 1)) {
      neighbors.push(pos + 1);
    }
    return neighbors;
  };
}

export default getNeighbors;
