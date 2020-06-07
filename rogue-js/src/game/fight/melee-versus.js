import inflictDamages from "./inflict-damages";
import {
  getWinMessage,
  getLooseMessage,
  getAttackMessage,
} from "./fight-messages";

function computeAR(o) {
  const { stats, baseClass } = o;
  const { strength, agility, luck } = stats;
  const { melee } = baseClass;

  const statsRange = strength / (agility + strength);
  const luckRange = luck / (strength + luck);
  const baseRange =
    melee + (statsRange * 0.8 + luckRange * 0.2) * Math.random() * (1 - melee);
  //
  return baseRange;
}

function computeDR(o) {
  const { stats, baseClass } = o;
  const { parade } = baseClass;
  const { agility, strength, luck } = stats;

  const statsRange = agility / (agility + strength);
  const luckRange = luck / (agility + luck);
  const baseRange =
    parade +
    (statsRange * 0.8 + luckRange * 0.2) * Math.random() * (1 - parade);
  return baseRange;
}

function computeDamages(attacker, weapon) {
  const { stats } = attacker;
  const { strength } = stats;
  const { getDamages } = weapon;
  const damages = getDamages() * strength;

  return damages;
}

function versus(attacker, defender, weapon) {
  const AR = computeAR(attacker);
  const DR = computeDR(defender);
  const attackMessage = getAttackMessage(attacker, defender, weapon);
  if (AR >= DR) {
    const damages = computeDamages(attacker, weapon);
    const nextDefender = inflictDamages(defender, damages);
    return [
      attacker,
      { ...nextDefender, path: undefined },
      [attackMessage, getWinMessage(attacker, nextDefender, damages)],
    ];
  }

  return [attacker, defender, [attackMessage, getLooseMessage(attacker)]];
}

export default versus;
