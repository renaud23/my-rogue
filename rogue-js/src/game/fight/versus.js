import { randomInt } from "../../commons";
import { fillMessage } from "../commons";
import PATTERNS from "../message-patterns";

function getWinMessage(att, deff, damages) {
  return fillMessage(PATTERNS.attackSuccess, { att, deff, damages });
}

function getLooseMessage(att, deff) {
  return fillMessage(PATTERNS.attackFailure, { att, deff });
}

function getDamagesMessage(att, deff, how) {
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
    // remove life
    const damages = computeDamages(attacker, weapon);

    return [
      attacker,
      defender,
      [attackMessage, getWinMessage(attacker, defender, damages)],
    ];
  }

  return [attacker, defender, [attackMessage, getLooseMessage(attacker)]];
}

export default versus;
