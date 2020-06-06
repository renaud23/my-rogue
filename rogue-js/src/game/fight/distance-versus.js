import { randomInt, computeDistance } from "../../commons";
import { fillMessage } from "../commons";
import inflictDamages from "./inflict-damages";
import PATTERNS from "../message-patterns";

function computeDamages(attacker, weapon) {
  return 0;
}

function tryToShoot(attacker, defender, weapon, distance) {
  const { stats } = attacker;
  const { luck, level, agility } = stats;
  const { range } = weapon;

  const DR = 100 - Math.trunc((distance * 100) / Math.pow(range + 1, 2));
  console.log({ DR, distance, range });
  // randomInt(agility + luck + 1);

  return true;
}

function versus(attacker, defender, weapon, state) {
  const { dungeon } = state;
  const { currentLevel, level } = attacker;

  const { ammo } = attacker;
  if (!ammo) {
    return [
      attacker,
      defender,
      [fillMessage(PATTERNS.noAmmoSelected, { weapon })],
    ];
  }
  const distance = computeDistance(
    attacker.position,
    defender.position,
    dungeon.getWidth(currentLevel === undefined ? level : currentLevel)
  );
  // validate ammo type
  tryToShoot(attacker, defender, weapon, distance);

  return [attacker, defender, []];
}

export default versus;
