import { randomInt } from "../../commons";

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
    console.log("attack success !", attacker, defender);
    // remove life
  }

  return [attacker, defender];
}

export default versus;
