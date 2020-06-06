import { randomInt } from "../../commons";
import { fillMessage } from "../commons";
import inflictDamages from "./inflict-damages";
import PATTERNS from "../message-patterns";

function computeDamages(attacker, weapon) {
  return 0;
}

function versus(attacker, defender, weapon) {
  console.log(weapon);
  return [attacker, defender, []];
}

export default versus;
