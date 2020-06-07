import { randomInt, computeDistance } from "../../commons";
import { fillMessage } from "../commons";
import inflictDamages from "./inflict-damages";
import PATTERNS from "../message-patterns";

function computeDamages(attacker, weapon) {
  return 0;
}

function sumStats(stats) {
  const { strength, agility, luck, endurance } = stats;
  return strength + agility + luck + endurance;
}

function tryToShoot(attacker, defender, weapon, distance) {
  const { stats } = attacker;
  const { luck, level, agility, strength } = stats;
  const { range } = weapon;
  const sum = sumStats(stats);

  const distanceRange = 0.7 + 0.3 * (1 - distance / Math.pow(range + 1, 2));
  const statsRange = agility / (agility + strength);
  const luckRange = luck / (agility + luck);
  const baseRange = 0.3 + statsRange * 0.5 + luckRange * 0.2;
  const finalRange = distanceRange * baseRange;
  const dice100 = randomInt(100) / 100;
  console.log(dice100, {
    finalRange,
    luckRange,
    statsRange,
    baseRange,
    distanceRange,
  });
  if (dice100 >= finalRange) {
    return false;
  }

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
