import { randomInt } from "./maths";

export * from "./maths";
export { getSegment } from "./get-segment";
export { default as isEmpty } from "./is-empty";

export function popOne(sac) {
  return sac.splice(randomInt(sac.length), 1)[0];
}

export function peekOne(sac) {
  return sac[randomInt(sac.length)];
}
