import {
  getSegment,
  antecedentPoint,
  distanceEucl2,
  pointProjection,
} from "../../commons";
import activateWait from "../activate-wait";
import { buildTurnPlay } from "../commons";
import { createRandomStats } from "../fight/fighter-stats";
import { isVisiblePosition, isEmptyPosition, getPositions } from "../commons";
import ATTACKS from "./eneny-attacks";
import { PLAYER_ACTIONS } from "../../commons";

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

function canBite(state, enemy) {
  const { player } = state;
  const { position: pp } = player;
  const { position } = enemy;
  const vois = getPositions(state, position, 1);
  return vois.reduce(function (a, pos) {
    return a || pos === pp;
  }, false);
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

function moveToPlayer(state, enemy) {
  const { dungeon, player } = state;
  const { currentLevel, position: pPos } = player;
  const { position: ePos } = enemy;
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

function follow(state, enemy) {
  const { dungeon, player } = state;
  const { currentLevel, position: pPos } = player;
  const { position: ePos } = enemy;
  const dw = dungeon.getWidth(currentLevel);

  //
  const [nx, ny] = moveToPlayer(state, enemy);
  const nePos = nx + ny * dw;

  if (nePos === ePos || nePos === pPos) {
    return [state, { ...enemy, activate: sleep }];
  }

  return [state, { ...enemy, position: nePos, activate: sleep }];
}

function sleep(state, enemy) {
  const { player, messages } = state;
  if (canSeePlayer(state, enemy)) {
    if (canBite(state, enemy)) {
      const { weapon } = enemy;
      const { versus } = weapon;
      const [nextRat, nextPlayer, nm] = versus(enemy, player, weapon, state);
      return [
        {
          ...state,
          activate: activateWait,
          player: {
            ...nextPlayer,
            action: {
              type: PLAYER_ACTIONS.menu,
              header: ["Appuyer sur le bouton A"],
            },
          },
          messages: [...messages, ...nm],
        },
        nextRat,
      ];
    }
    return follow(state, enemy);
  }
  return [state, enemy];
}

export function createRat(xpLevel = 1) {
  return {
    activate: sleep,
    fov: 8,
    turn: buildTurnPlay(2),
    desc: "un rat",
    stats: { ...createRandomStats(xpLevel), level: xpLevel, life: 10 },
    baseClass: { melee: 0.4, distance: 0.2, parade: 0.2 },
    weapon: ATTACKS.nibbles,
  };
}

/*

if seePlayer
  path = []
  lastPos = posPlayer
  return moveTo(lastPos)
if lastPos !== null
  path = A*(lastPos)
  lastPos = undefined
  return consume(path)
if path not empty
  return consume(path)
return sleep
*/
