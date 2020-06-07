import { randomInt, computeDistance } from "../../commons";
import { fillMessage } from "../commons";
import inflictDamages from "./inflict-damages";
import {
  getWinMessage,
  getLooseMessage,
  getAttackMessage,
} from "./fight-messages";

import PATTERNS from "../message-patterns";

function computeDamages(attacker) {
  const { weapon, ammo } = attacker;

  return weapon.getDamages() * ammo.getDamages();
}

function sumStats(stats) {
  const { strength, agility, luck, endurance } = stats;
  return strength + agility + luck + endurance;
}

function tryToShoot(attacker, defender, weapon, distance) {
  const { stats, baseClass } = attacker;
  const { luck, agility, strength } = stats;
  const { distance: baseDist } = baseClass;
  const { range } = weapon;

  const distanceRange = 0.7 + 0.3 * (1 - distance / Math.pow(range + 1, 2));
  const statsRange = agility / (agility + strength);
  const luckRange = luck / (agility + luck);
  const baseRange =
    baseDist + (statsRange * 0.8 + luckRange * 0.2) * (1 - baseDist);
  const finalRange = distanceRange * baseRange;
  const dice100 = randomInt(100) / 100;
  if (dice100 >= finalRange) {
    return false;
  }

  return true;
}

function consumeAmmo(player) {
  const { ammo } = player;
  const { how } = ammo;
  if (how > 1) {
    const nextAmmo = { ...ammo, how: how - 1 };
    return [
      { ...player, ammo: nextAmmo },
      fillMessage(PATTERNS.restAmmo, { ammo: nextAmmo }),
    ];
  }
  return [
    { ...player, ammo: undefined },
    fillMessage(PATTERNS.runOutOfAmmo, { ammo }),
  ];
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
  // TODO validate ammo type
  const attackMessage = getAttackMessage(attacker, defender, weapon);
  const [nextAttacker, ammoMsg] = consumeAmmo(attacker);
  if (tryToShoot(attacker, defender, weapon, distance)) {
    const damages = computeDamages(attacker);
    const nextDefender = inflictDamages(defender, damages);
    return [
      nextAttacker,
      nextDefender,
      [attackMessage, getWinMessage(attacker, nextDefender, damages), ammoMsg],
    ];
  }

  return [
    nextAttacker,
    defender,
    [attackMessage, getLooseMessage(nextAttacker), ammoMsg],
  ];
}

export default versus;
