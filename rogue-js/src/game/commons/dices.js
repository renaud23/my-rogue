import { randomInt } from "../../commons";

function one6sidesDice() {
  return 1 + randomInt(6);
}

export function create6sidesDices(how) {
  return function () {
    return new Array(how).fill(one6sidesDice()).reduce(function (a, dice) {
      return a + dice;
    }, 0);
  };
}

export const one6SidesDice = create6sidesDices(1);
export const two6SidesDice = create6sidesDices(2);
