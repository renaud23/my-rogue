import isEmptyPosition from "./is-empty-position";

function findDestination(state, pos, destination) {
  const { player, dungeon } = state;
  const { currentLevel } = player;
  const width = dungeon.getWidth(currentLevel);
  if (
    pos - width === destination ||
    pos + width === destination ||
    pos + 1 === destination ||
    pos - 1 === destination
  ) {
    return true;
  }

  return false;
}

function findNeighbors(state, pos, isEmpty) {
  const { player, dungeon } = state;
  const { currentLevel } = player;
  const width = dungeon.getWidth(currentLevel);
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
}

function getNeighbors(state, isEmpty = isEmptyPosition) {
  return function (pos, destination) {
    if (findDestination(state, pos, destination)) {
      return [destination];
    }
    return findNeighbors(state, pos, isEmpty);
  };
}

export default getNeighbors;
