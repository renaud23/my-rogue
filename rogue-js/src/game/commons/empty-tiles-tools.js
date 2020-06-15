import { randomInt } from "../../commons";

export function popOne(empties, level) {
  const witch = randomInt(empties[level].length - 1);
  return empties[level].splice(witch, 1)[0];
}

export function peekOne(empties, level) {
  return empties[level][randomInt(empties[level].length - 1)];
}
