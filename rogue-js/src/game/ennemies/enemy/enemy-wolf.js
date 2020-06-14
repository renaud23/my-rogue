import { buildTurnPlay } from "../../commons";
import { createRandomStats } from "../../fight/fighter-stats";
import { computeMaxLife } from "../../fight";
import ATTACKS from "../enemy-attacks";
import { TYPE_ENNEMIES } from "../commons/type-ennemies";
import canSeePlayer from "../commons/can-see-player";
import canBite from "../commons/can-bite";
import attack from "../commons/attack";
import { computePath, consumePath, isPath } from "./commons-path";

function activate(state, enemy) {
  if (canSeePlayer(state, enemy)) {
    const { player } = state;
    const { position: playerPos } = player;
    const { position: enemyPos, path } = enemy;
    if (canBite(state, enemy)) {
      const [nextState, nextEnemy] = attack(state, enemy);
      return [nextState, { ...nextEnemy, path: undefined }];
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
    turn: buildTurnPlay(3),
    desc: "un loup",
    attack: 0,
    attackLimite: 2,
    stats: computeMaxLife(
      {
        ...createRandomStats(level),
        level,
        life: 0,
      },
      5
    ),
    baseClass: { melee: 0.4, distance: 0, parade: 0.3 },
    weapon: ATTACKS.bite,
  };
}

export default createWolf;
