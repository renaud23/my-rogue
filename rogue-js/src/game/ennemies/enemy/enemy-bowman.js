import { buildTurnPlay } from "../../commons";
import { createRandomStats } from "../../fight/fighter-stats";
import { computeMaxLife } from "../../fight";
import ATTACKS from "../enemy-attacks";
import { TYPE_ENNEMIES } from "../commons/type-ennemies";
import activate from "./activate-distance";
import { createArrows, createBow } from "../../objects";

function createBowman(level) {
  return {
    type: TYPE_ENNEMIES.bowman,
    activate: activate,
    fov: 6,
    turn: buildTurnPlay(2),
    desc: "Un archer",
    attack: 0,
    attackLimite: 1,
    stats: computeMaxLife(
      {
        ...createRandomStats(level),
        level,
        life: 0,
      },
      5
    ),
    baseClass: { melee: 0.2, distance: 0.4, parade: 0.3 },
    weapon: createBow(),
    ammo: createArrows(5),
  };
}

export default createBowman;
