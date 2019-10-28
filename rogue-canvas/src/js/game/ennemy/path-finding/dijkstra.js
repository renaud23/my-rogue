import { distanceEucl, posToCoord } from "../../commons";
import { refillPath, getNeighbors } from "./common";
import Queue from "../../commons/priority-queue";

export const dijkstraPath = game => (from, to) => {
  const { dungeon } = game;
  const { width } = dungeon;
  let frontiere = new Queue();
  frontiere.put(from, 0);
  const toCoord = posToCoord(width)(to);
  const visited = {};
  while (!frontiere.empty()) {
    const current = frontiere.get();

    if (current === to) {
      break;
    }
    const neightbors = getNeighbors(game)(current);

    neightbors.forEach(n => {
      if (!(n in visited)) {
        const dist = distanceEucl(posToCoord(width)(n), toCoord);
        frontiere.put(n, dist);
        visited[n] = current;
      }
    });
  }
  const [first, ...path] = refillPath(visited)(to);
  return path;
};
