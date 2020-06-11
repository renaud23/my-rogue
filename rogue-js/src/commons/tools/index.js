import { randomInt } from "./maths";

export * from "./maths";
export * from "./get-segment";

export function popOne(sac) {
  return sac.splice(randomInt(sac.length), 1)[0];
}

export function peekOne(sac) {
  return sac[randomInt(sac.length)];
}

export function popThisOne(sac, one) {
  return sac.splice(sac.indexOf(one), 1);
}
