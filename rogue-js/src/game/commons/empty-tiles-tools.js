import { randomInt } from "../../commons";

export function popOne(empties, level) {
  const witch = randomInt(empties[level].length - 1);
  return empties[level].splice(witch, 1)[0];
}

export function peekOne(empties, level) {
  return empties[level][randomInt(empties[level].length - 1)];
}

export default function createEmpties(empties) {
  const newEmpties = empties.map(function (level) {
    return [...level];
  });
  return {
    popOne: function (level) {
      const witch = randomInt(newEmpties[level].length - 1);
      return newEmpties[level].splice(witch, 1)[0];
    },
    peekOne: function (level) {
      const witch = randomInt(newEmpties[level].length - 1);
      return newEmpties[level][witch];
    },
    removePosition(level, position) {
      const witch = newEmpties[level].indexOf(position);
      if (witch !== -1) {
        newEmpties[level].splice(witch, 1);
      }
    },
    count: function (level) {
      return newEmpties[level].length;
    },
  };
}
