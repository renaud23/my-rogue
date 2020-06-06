import typeObject from "./type-object";
import { create6sidesDices } from "../commons/dices";
import { meleeVersus } from "../fight";

let INDEX = 0;

export const WEAPONS_MAP = {
  knife: {
    code: 1000,
    desc: "un couteau",
    size: 1,
    type: typeObject.weapon,
    getDamages: create6sidesDices(1),
  },
  sword: {
    code: 1001,
    desc: "une épée",
    size: 2,
    type: typeObject.weapon,
    getDamages: create6sidesDices(2),
  },
  armageddon: {
    code: 1002,
    desc: "Armageddon",
    size: 2,
    type: typeObject.weapon,
    getDamages: create6sidesDices(18),
  },
  woodenBow: {
    code: 1003,
    desc: "Armageddon",
    size: 2,
    type: typeObject.weapon,
    getDamages: create6sidesDices(1),
  },
};

export const WEAPONS_CODE_TO_OBJECT = Object.keys(WEAPONS_MAP).reduce(function (
  a,
  o
) {
  const { code } = o;
  return { ...a, [code]: o };
},
{});

export const WEAPONS_LIST = Object.values(WEAPONS_MAP);

export function createKnife() {
  const knife = {
    ...WEAPONS_MAP.knife,
    takeable: true,
    id: `knife-${INDEX++}`,
    versus: meleeVersus,
  };

  const todo = [];
  return { ...knife, todo };
}

export function createSword() {
  const sword = {
    ...WEAPONS_MAP.sword,
    takeable: true,
    id: `sword-${INDEX++}`,
    versus: meleeVersus,
  };

  const todo = [];
  return { ...sword, todo };
}

export function createArmageddon() {
  const sword = {
    ...WEAPONS_MAP.armageddon,
    takeable: true,
    id: `armageddon-${INDEX++}`,
    versus: meleeVersus,
  };

  const todo = [];
  return { ...sword, todo };
}
