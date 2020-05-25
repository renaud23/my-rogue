import { peekOne, randomInt } from "../../commons";
import {
  getSegment,
  antecedantPoint,
  distanceEucl2,
  pointProjection,
} from "../../commons";
import { isVisiblePosition } from "../commons";

let INDEX = 0;

const fuck = ([x, y]) => ({ x, y });

function canSeePlayer(state, rat) {
  const { dungeon, player } = state;
  const { currentLevel, position: ppos } = player;
  const { fov, position: rpos, level: ratLevel } = rat;
  if (ratLevel !== currentLevel) return false;
  const dw = dungeon.getWidth(currentLevel);

  const a = antecedantPoint(rpos, dw);
  const b = antecedantPoint(ppos, dw);
  const dist = distanceEucl2(a, b);

  if (dist <= fov * fov) {
    const segment = getSegment(a, b);
    const positions = segment.map(([x, y]) => pointProjection([x, y], dw));

    return positions.reduce(function (a, pos, i) {
      if (i === 0) {
        return true;
      }
      return a && isVisiblePosition(state, ratLevel, pos);
    }, true);
  }
  return false;
}

function follow(state, rat) {
  console.log("follow", rat);
  return state;
}

function switchEnnemyActivate(ennemies, ennemy, cally) {
  const { id, level } = ennemy;
  return ennemies.map(function (level, i) {
    if (i === level) {
      return level.map(function (r) {
        if (r.id === id) {
          return { ...r, activate: cally };
        }

        return r;
      });
    }
    return level;
  });
}

function sleep(state, rat) {
  const { player, ennemies } = state;
  const { position: pp } = player;
  if (canSeePlayer(state, rat)) {
    const ne = ennemies.map(function (level, i) {
      if (i === rat.level) {
        return level.map(function (r) {
          if (r.id === rat.id) {
            return { ...r, activate: follow };
          }

          return r;
        });
      }
      return level;
    });
    return { ...state, ennemies: ne };
  }

  return state;
}

function createRat() {
  return { activate: sleep, fov: 5, data: { desc: "un rat puant" } };
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
