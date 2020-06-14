import { buildTurnPlay } from "../../commons";
import { createRandomStats } from "../../fight/fighter-stats";
import { computeMaxLife } from "../../fight";
import ATTACKS from "../enemy-attacks";
import { aStarPath } from "../path-finding";
import { TYPE_ENNEMIES } from "../commons/type-ennemies";
import canSeePlayer from "../commons/can-see-player";
import canBite from "../commons/can-bite";
import attack from "../commons/attack";

function createBowman(level) {
  return {
    type: TYPE_ENNEMIES.wolf,
    activate: activate,
    fov: 6,
    turn: buildTurnPlay(3),
    desc: "Un archer",
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

export default createBowman;
