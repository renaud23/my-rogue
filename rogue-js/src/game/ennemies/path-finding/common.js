import { isEmptyPosition } from "../../commons";

export const refillPath = (visited) => (last) => {
  return visited[last] ? [...refillPath(visited)(visited[last]), last] : [last];
};

export function getNeighbors(game) {
  return (pos) => {
    const { dungeon } = game;
    const { width } = dungeon;
    const neighbors = [];
    if (isEmptyPosition(game)(pos - width)) {
      neighbors.push(pos - width);
    }
    if (isEmptyPosition(game)(pos + width)) {
      neighbors.push(pos + width);
    }
    if (isEmptyPosition(game)(pos - 1)) {
      neighbors.push(pos - 1);
    }
    if (isEmptyPosition(game)(pos + 1)) {
      neighbors.push(pos + 1);
    }
    return neighbors;
  };

//   export const posToCoord = (width) => (pos) => ({
//     x: pos % width,
//     y: Math.trunc(pos / width),
//   });
// }
