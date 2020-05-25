import { peekOne, randomInt } from "../../commons";
import {
  getSegment,
  antecedentPoint,
  distanceEucl2,
  pointProjection,
} from "../../commons";
import { buildTurnPlay } from "../commons";
import { isVisiblePosition, isEmptyPosition } from "../commons";

let INDEX = 0;

function canSeePlayer(state, ennemy) {
  const { dungeon, player } = state;
  const { currentLevel, position: ppos } = player;
  const { fov, position: rpos, level } = ennemy;
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

function canBite(state, rat) {
  return false;
}

function getVariation(delta) {
  if (delta === 0) {
    return 0;
  }
  if (delta < 0) {
    return -1;
  }
  return 1;
}

function moveToPlayer(state, ennemy) {
  const { dungeon, player } = state;
  const { currentLevel, position: pPos } = player;
  const { position: ePos } = ennemy;
  const dw = dungeon.getWidth(currentLevel);
  const [px, py] = antecedentPoint(pPos, dw);
  const [ex, ey] = antecedentPoint(ePos, dw);

  const dx = px - ex;
  const vx = getVariation(dx);
  const dy = py - ey;
  const vy = getVariation(dy);

  if (
    Math.abs(dx) > Math.abs(dy) &&
    isEmptyPosition(state, currentLevel, pointProjection([ex + vx, ey], dw))
  ) {
    return [ex + vx, ey];
  }
  if (
    isEmptyPosition(state, currentLevel, pointProjection([ex, ey + vy], dw))
  ) {
    return [ex, ey + vy];
  }
  return [ex, ey];
}

function follow(state, ennemy) {
  const { dungeon, player } = state;
  const { currentLevel, position: pPos } = player;
  const { position: ePos } = ennemy;
  const dw = dungeon.getWidth(currentLevel);

  //
  const [nx, ny] = moveToPlayer(state, ennemy);
  const nePos = nx + ny * dw;

  if (nePos === ePos || nePos === pPos) {
    return [state, { ...ennemy, activate: sleep }];
  }

  return [state, { ...ennemy, position: nePos, activate: follow }];
}

function sleep(state, rat) {
  const { player, ennemies } = state;
  const { position: pp } = player;
  if (canSeePlayer(state, rat)) {
    return follow(state, rat);
  }
  return [state, rat];
}

function createRat() {
  return {
    activate: sleep,
    fov: 8,
    turn: buildTurnPlay(2),
    desc: "un vilain rat",
  };
}

function createLevelRat(state, level) {
  const { dungeon } = state;
  const how = 2 + randomInt(3);
  return new Array(how).fill(null).map(function () {
    const position = peekOne(dungeon.getEmptyTiles(level));
    return { id: `rat-${INDEX++}`, position, level, ...createRat() };
  });
}

export function createRatsDungeon(state) {
  const { dungeon } = state;
  const dungeonHeight = dungeon.getDungeonHeight();

  const rats = new Array(dungeonHeight)
    .fill(null)
    .reduce(function (a, _, level) {
      return [...a, createLevelRat(state, level)];
    }, []);

  return rats;
}
