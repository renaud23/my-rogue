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
  const { strength, luck, level } = stats;
  const effectiveStrength = strength * level * 2;
  const lucky = randomInt(luck);

  return randomInt(effectiveStrength) + lucky * level;
}

function computeDR(o) {
  const { stats } = o;
  const { agility, luck, level } = stats;
  const effectiveAgility = agility * level * 2;
  const lucky = randomInt(luck);

  return randomInt(effectiveAgility) + lucky * level;
}

function computeDamages(attacker, weapon) {
  const { stats } = attacker;
  const { level, luck } = stats;
  const { getDamages } = weapon;
  const damages = (getDamages() + randomInt(luck)) * level;

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

  if (AR >= DR) {
    const damages = computeDamages(attacker, weapon);
    const nextDefender = inflictDamages(defender, damages);
    return [
      attacker,
      nextDefender,
      [attackMessage, getWinMessage(attacker, nextDefender, damages)],
    ];
  }

  return [attacker, defender, [attackMessage, getLooseMessage(attacker)]];
}

export default versus;
