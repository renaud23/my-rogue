import { randomInt } from "../../commons";

export function one6SidesDice() {
  return 1 + randomInt(6);
}

export function one4SidesDice() {
  return 1 + randomInt(3);
}

export function create6sidesDices(how) {
  return () => {
    return new Array(how)
      .fill(one6SidesDice())
      .reduce((a, dice) => a + dice, 0);
  };
}

export function create4sidesDices(how) {
  return () => {
    return new Array(how)
      .fill(one4SidesDice())
      .reduce((a, dice) => a + dice, 0);
  };
}

export const two6SidesDice = create6sidesDices(2);
export const three6SidesDice = create6sidesDices(3);
