import { randomInt } from "../../commons";
import { fillMessage } from "../commons";
import inflictDamages from "./inflict-damages";
import PATTERNS from "../message-patterns";

function getWinMessage(att, deff, damages) {
  return fillMessage(PATTERNS.attackSuccess, { att, deff, damages });
}

function getLooseMessage(att, deff) {
  return PATTERNS.attackFailure;
}

function computeAR(o) {
  const { stats } = o;
  const { strength, agility, luck, level } = stats;

  const statsRange = strength / (agility + strength);
  const luckRange = luck / (strength + luck);
  const baseRange =
    0.3 + (statsRange * 0.5 + luckRange * 0.2) * Math.random() * 0.7;

  return baseRange;
  // const effectiveStrength = (strength + luck) * 2;
  // return randomInt(effectiveStrength) * level;
}

function computeDR(o) {
  const { stats } = o;
  const { agility, strength, luck, level } = stats;

  const statsRange = agility / (agility + strength);
  const luckRange = luck / (agility + luck);
  const baseRange =
    0.3 + (statsRange * 0.5 + luckRange * 0.2) * Math.random() * 0.7;

  return baseRange;
  // const effectiveAgility = (agility + luck) * 2;
  // return randomInt(effectiveAgility) * level;
}

function computeDamages(attacker, weapon) {
  const { stats } = attacker;
  const { level, luck, strength } = stats;
  const { getDamages } = weapon;
  const damages = (getDamages() + randomInt(luck + strength)) * level;

  return damages;
}

function versus(attacker, defender, weapon) {
  const AR = computeAR(attacker);
  const DR = computeDR(defender);
  const attackMessage = fillMessage(PATTERNS.attack, {
    att: attacker,
    deff: defender,
    weapon,
  });
  console.log({ AR, DR, a: attacker.stats, d: defender.stats });
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
