import { randomInt } from "../../commons";
import { fillMessage } from "../commons";
import PATTERNS from "../message-patterns";

function getWin(att, deff) {
  return fillMessage(PATTERNS.attackSuccess, { att, deff });
}

function getLoose(att, deff) {
  return fillMessage(PATTERNS.attackFailure, { att, deff });
}

function getDamages(att, deff, how) {
  return fillMessage(PATTERNS.damages, { att, deff, how });
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

function versus(attacker, defender, weapon) {
  const AR = computeAR(attacker);
  const DR = computeDR(defender);
  if (AR > DR) {
    // remove life
    return [
      attacker,
      defender,
      [getWin(attacker, defender), getDamages(attacker, defender, 0)],
    ];
  }

  return [attacker, defender, [getLoose(attacker, defender)]];
}

export default versus;
