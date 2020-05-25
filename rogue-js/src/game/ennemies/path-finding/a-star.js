import { distanceEucl } from "../../commons";
import { refillPath, getNeighbors, posToCoord } from "./common";
import Queue from "../../commons/priority-queue";

// isEmpty(state, level, position)

export const astarPath = (state) => (from, to) => {
  const { dungeon, player } = state;

  const { width } = dungeon;
  const visited = {};
  const costMap = {};
  let frontiere = new Queue();
  costMap[from] = 0;
  frontiere.put(from, 0);
  const toCoord = posToCoord(width)(to);

  while (!frontiere.empty()) {
    const current = frontiere.get();
    const newCost = costMap[current] + 1;

    if (current === to) {
      break;
    }
    const neightbors = getNeighbors(state)(current);

    neightbors.forEach((n) => {
      if (!(n in visited)) {
        const dist = distanceEucl(posToCoord(width)(n), toCoord) + newCost;
        frontiere.put(n, dist);
        visited[n] = current;
        costMap[n] = dist;
      }
    });
  }

  const [first, ...path] = refillPath(visited)(to);
  return path;
};
