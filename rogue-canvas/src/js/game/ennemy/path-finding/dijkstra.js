import * as tools from "../../common-tools";
import { getNeighbors, refillPath } from "./common";

export const dijkstraPath = game => (from, to) => {
  const { dungeon } = game;
  const { width } = dungeon;
  let frontiere = new tools.Queue();
  frontiere.put(from, 0);
  const toCoord = tools.posToCoord(width)(to);
  const visited = {};
  while (!frontiere.empty()) {
    const current = frontiere.get();

    if (current === to) {
      break;
    }
    const neightbors = getNeighbors(game)(current);

    neightbors.forEach(n => {
      if (!(n in visited)) {
        const dist = tools.distanceEucl(tools.posToCoord(width)(n), toCoord);
        frontiere.put(n, dist);
        visited[n] = current;
      }
    });
  }
  const [first, ...path] = refillPath(visited)(to);
  return path;
};
