import PATTERNS from "../message-patterns";
import { fillMessage } from "../commons";

export function getWinMessage(att, deff, damages) {
  return fillMessage(PATTERNS.attackSuccess, { att, deff, damages });
}

export function getLooseMessage(att, deff) {
  return PATTERNS.attackFailure;
}

export function getAttackMessage(att, deff, weapon) {
  return fillMessage(PATTERNS.attack, {
    att,
    deff,
    weapon,
  });
}
