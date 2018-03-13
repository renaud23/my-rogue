import Wolf from "./monster/wolf";

export const MONSTER_TYPE = { WOLF: "WOLF" };

export const createMonster = (type, x, y) => {
  switch (type) {
    case MONSTER_TYPE.WOLF:
      return new Wolf(x, y);
    default:
      return null;
  }
};
