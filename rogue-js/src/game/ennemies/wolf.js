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
import { TYPE_ENNEMIES } from "./commons/type-ennemies";
import canSeePlayer from "./commons/can-see-player";

function activate(state, enemy) {
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
