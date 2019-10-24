import { distanceEucl, posToCoord } from "../../commons";
import { refillPath, getNeighbors } from "./common";
import Queue from "../../commons/priority-queue";

export const astarPath = game => (from, to) => {
  const { dungeon } = game;
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
    const neightbors = getNeighbors(game)(current);

    neightbors.forEach(n => {
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
