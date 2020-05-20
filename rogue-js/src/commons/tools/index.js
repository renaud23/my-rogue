import { randomInt } from "./maths";

export * from "./maths";
export { getSegment } from "./get-segment";

export function peekOne(sac) {
  return sac.splice(randomInt(sac.length), 1)[0];
}
