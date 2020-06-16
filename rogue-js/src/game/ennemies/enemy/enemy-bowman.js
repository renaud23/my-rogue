import { buildTurnPlay } from "../../commons";
import { createRandomStats } from "../../fight/fighter-stats";
import { computeMaxLife } from "../../fight";
import { TYPE_ENNEMIES } from "../commons/type-ennemies";
import activate from "./activate-distance";
import { createArrows, createBow, createKnife } from "../../objects";
import createInventory from "../../player/inventory";

function createBowman(level) {
  const knife = createKnife();
  const weapon = createBow();
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
    baseClass: { melee: 0.2, distance: 0.3, parade: 0.3 },
    weapon,
    ammo: createArrows(3),
    inventory: createInventory(1, knife),
    loot: function (enemy) {
      const { position, level, ammo } = enemy;

      const loot = [
        { ...weapon, position, level },
        { ...knife, position, level },
      ];
      if (ammo && ammo.how) {
        return [...loot, { ...ammo, position, level }];
      }
      return loot;
    },
  };
}

export default createBowman;
