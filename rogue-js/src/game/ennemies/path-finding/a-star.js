import createGetNeighbors from "../../commons/create-get-neighbors";
import Queue from "../../../commons/priority-queue";

function distanceEucl([x1, y1], [x2, y2]) {
  const xx = x2 - x1;
  const yy = y2 - y1;
  return Math.pow(xx, 2) + Math.pow(yy, 2);
}

function antecedentPoint(position, width) {
  return [position % width, Math.trunc(position / width)];
}

const refillPath = (visited) => (last) => {
  return last in visited
    ? [...refillPath(visited)(visited[last]), last]
    : [last];
};

export default function astarPath(state, position, isEmpty) {
  const { player, dungeon } = state;
  const { currentLevel } = player;
  const width = dungeon.getWidth(currentLevel);
  const getNeighbors = createGetNeighbors(state, isEmpty);

  return function (from, to) {
    const visited = { [from]: undefined };
    const costMap = {};
    let frontiere = new Queue();
    costMap[from] = 0;
    frontiere.put(from, 0);
    const toCoord = antecedentPoint(to, width);

    while (!frontiere.empty()) {
      const current = frontiere.get();
      const newCost = costMap[current] + 1;

      if (current === to) {
        break;
      }
      const neightbors = getNeighbors(current, to);

      const filt = neightbors.reduce(
        (a, p) => (p in visited ? a : [...a, p]),
        []
      );

      filt.forEach((n) => {
        if (!(n in visited)) {
          const dist =
            distanceEucl(antecedentPoint(n, width), toCoord) + newCost;

          frontiere.put(n, dist);
          visited[n] = current;
          costMap[n] = dist;
        }
      });
    }
    const [_, ...path] = refillPath(visited)(to);

    return path;
  };
}
