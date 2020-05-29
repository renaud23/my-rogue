import { randomInt } from "../../commons";
import { fillMessage } from "../commons";

// const test = {
//   att: { desc: "Renaud" },
//   deff: { desc: "un rat", stats: { level: 10 } },
// };
// console.log(
//   interpreter(
//     "<yellow>Attaque réussie, ${att.desc} sur </yellow><blue>${deff.desc} level ${deff.stats.level}</blue>.",
//     test
//   )
// );

function getWin(att, deff) {
  return [
    fillMessage(
      "<yellow>Attaque réussie, ${att.desc} sur </yellow><blue>${deff.desc} level ${deff.stats.level}</blue>.",
      { att, deff }
    ),
  ];
}

function getLoose(att, deff) {
  return [
    fillMessage(
      "<yellow>Attaque échec, ${att.desc} sur </yellow><blue>${deff.desc} level ${deff.stats.level}</blue>.",
      { att, deff }
    ),
  ];
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
    return [attacker, defender, getWin(attacker, defender)];
  }

  return [attacker, defender, getLoose(attacker, defender)];
}

export default versus;
