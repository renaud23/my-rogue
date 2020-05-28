import { antecedentPoint } from "../../commons";

function getPositionsAround(state, position, limite) {
  if (!limite) {
    return [position];
  }
  const { dungeon, player } = state;
  const { currentLevel } = player;
  const width = dungeon.getWidth(currentLevel);
  const height = dungeon.getHeight(currentLevel);
  const [nx, ny] = antecedentPoint(position, width);

  const minx = Math.max(nx - limite, 0);
  const maxx = Math.min(nx + limite, width - 1);
  const miny = Math.max(ny - limite, 0);
  const maxy = Math.min(ny + limite, height - 1);
  const rw = maxx - minx + 1;
  const rh = maxy - miny + 1;
  return new Array(rw * rh).fill(null).map(function (_, i) {
    const [px, py] = antecedentPoint(i, rw);
    return px + minx + (py + miny) * width;
  });
}

export default getPositionsAround;
