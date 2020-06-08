import {
  getSegment,
  antecedentPoint,
  distanceEucl2,
  pointProjection,
} from "../../../commons";
import { isVisiblePosition } from "../../commons";

function canSeePlayer(state, enemy) {
  const { dungeon, player } = state;
  const { currentLevel, position: ppos } = player;
  const { fov, position: rpos, level } = enemy;
  if (level !== currentLevel) return false;
  const dw = dungeon.getWidth(currentLevel);

  const a = antecedentPoint(rpos, dw);
  const b = antecedentPoint(ppos, dw);
  const dist = distanceEucl2(a, b);

  if (dist <= fov * fov) {
    const segment = getSegment(a, b);
    const positions = segment.map(([x, y]) => pointProjection([x, y], dw));

    return positions.reduce(function (a, pos, i) {
      if (i === 0) {
        return true;
      }
      return a && isVisiblePosition(state, level, pos);
    }, true);
  }
  return false;
}

export default canSeePlayer;
