import {
  getSegment,
  antecedentPoint,
  distanceEucl2,
  pointProjection,
} from "../../commons";
import activateWait from "../activate-wait";
import { buildTurnPlay } from "../commons";
import { createRandomStats } from "../fight/fighter-stats";
import { computeMaxLife } from "../fight";
import { isVisiblePosition, isEmptyPosition, getPositions } from "../commons";
import ATTACKS from "./eneny-attacks";
import { PLAYER_ACTIONS } from "../../commons";
import { aStarPath } from "./path-finding";
import { TYPE_ENNEMIES } from "./commons/type-ennemies";
import canSeePlayer from "./commons/can-see-player";
import canBite from "./commons/can-bite";

function computePath(state, enemy) {
  const { player } = state;
  const { position: playerPos } = player;
  const { position: enemyPos } = enemy;
  const [_, position, path] = aStarPath(state)(enemyPos, playerPos);
  return [state, { ...enemy, position, path: path.length ? path : undefined }];
}

function consumePath(state, enemy) {
  const { player } = state;
  const { path } = player;
  const [position, rest] = path;

  return [state, { ...enemy, position, path: rest.length ? rest : undefined }];
}

function isPath(enemy) {
  return enemy.path;
}

function activate(state, enemy) {
  if (canSeePlayer(state, enemy)) {
    const { player } = state;
    const { position: playerPos } = player;
    const { position: enemyPos, path } = enemy;
    if (canBite(state, enemy)) {
      return [state, { ...enemy, path: undefined }];
    }
    if (isPath(enemy)) {
      return consumePath(state, enemy);
    }
    return computePath(state, enemy);
  }
  if (isPath(enemy)) {
    return consumePath(state, enemy);
  }
  return [state, enemy];
}

function createWolf(level) {
  return {
    type: TYPE_ENNEMIES.wolf,
    activate: activate,
    fov: 6,
    turn: buildTurnPlay(2),
    desc: "un loup",
    stats: computeMaxLife(
      {
        ...createRandomStats(level),
        level,
        life: 0,
      },
      5
    ),
    baseClass: { melee: 0.5, distance: 0, parade: 0.2 },
    weapon: ATTACKS.nibbles,
  };
}

export default createWolf;
