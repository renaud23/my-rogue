import * as tools from "../../common-tools";
import { getNeighbors, refillPath } from "./common";

export const astarPath = game => (from, to) => {
  const { dungeon } = game;
  const { width } = dungeon;
  const visited = {};
  const costMap = {};
  let frontiere = new tools.Queue();
  costMap[from] = 0;
  frontiere.put(from, 0);
  const toCoord = tools.posToCoord(width)(to);

  while (!frontiere.empty()) {
    const current = frontiere.get();
    const newCost = costMap[current] + 1;

    if (current === to) {
      break;
    }
    const neightbors = getNeighbors(game)(current);

    neightbors.forEach(n => {
      if (!(n in visited)) {
        const dist =
          tools.distanceEucl(tools.posToCoord(width)(n), toCoord) + newCost;
        frontiere.put(n, dist);
        visited[n] = current;
        costMap[n] = dist;
      }
    });
  }

  const [first, ...path] = refillPath(visited)(to);
  return path;
};
